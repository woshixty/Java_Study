package net.qiujuer.library.clink.core;

import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.box.StringSendPacket;
import net.qiujuer.library.clink.impl.SocketChannelAdapter;
import net.qiujuer.library.clink.impl.async.AsyncReceiveDispatcher;
import net.qiujuer.library.clink.impl.async.AsyncSendDispatcher;

import java.io.Closeable;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;

public class Connector implements Closeable, SocketChannelAdapter.OnChannelStatusChangedListener {
    private UUID key = UUID.randomUUID();
    private SocketChannel channel;
    private Sender sender;
    private Receiver receiver;
    private SendDispatcher sendDispatcher;
    private ReceiveDispatcher receiveDispatcher;

    public void setup(SocketChannel socketChannel) throws IOException {
        this.channel = socketChannel;
        //拿到单例
        IoContext context = IoContext.get();
        //初始化
        SocketChannelAdapter adapter = new SocketChannelAdapter(channel, context.getIoProvider(), this);
        this.sender = adapter;
        this.receiver = adapter;
        //读取数据
//        readNextMessage();
        sendDispatcher = new AsyncSendDispatcher(sender);
        receiveDispatcher = new AsyncReceiveDispatcher(receiver, receivePacketCallback);

        //启动接受
        receiveDispatcher.start();
    }

    public void sent(String msg) {
        SendPacket packet = new StringSendPacket(msg);
        sendDispatcher.send(packet);
    }

//    private void readNextMessage() {
//        //接收者不为空
//        if (receiver != null) {
//            try {
//                receiver.receiveAsync(echoReceiveListener);
//            } catch (IOException e) {
//                System.out.println("开始接收数据异常：" + e.getMessage());
//            }
//        }
//    }

    @Override
    public void close() throws IOException {
        receiveDispatcher.close();
        sendDispatcher.close();
        sender.close();
        receiver.close();
        channel.close();
    }

    @Override
    public void onChannelClosed(SocketChannel channel) {

    }

//    private IoArgs.IoArgsEventListener echoReceiveListener = new IoArgs.IoArgsEventListener() {
//        @Override
//        public void onStarted(IoArgs args) {
//
//        }
//
//        @Override
//        public void onCompleted(IoArgs args) {
//            // 打印
//            onReceiveNewMessage(args.bufferString());
//            // 读取下一条数据
//            readNextMessage();
//        }
//    };

    private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = packet -> {
        if (packet instanceof StringReceivePacket) {
            String msg = ((StringReceivePacket) packet).string();
            onReceiveNewMessage(msg);
        }
    };


    //接收到数据的时候打印
    protected void onReceiveNewMessage(String str) {
        System.out.println(key.toString() + ":" + str);
    }
}