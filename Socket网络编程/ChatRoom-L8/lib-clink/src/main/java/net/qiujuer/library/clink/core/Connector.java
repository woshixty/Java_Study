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

/**
 * 连接
 * 当关闭的时候的回调：SocketChannelAdapter.OnChannelStatusChangedListener
 */
public class Connector implements Closeable, SocketChannelAdapter.OnChannelStatusChangedListener {
    // 代表这个连接的唯一性
    private UUID key = UUID.randomUUID();
    // 依赖于SocketChannel
    private SocketChannel channel;
    // 发送者和接收者
    private Sender sender;
    private Receiver receiver;
    private SendDispatcher sendDispatcher;
    private ReceiveDispatcher receiveDispatcher;

    /**
     * 建立连接
     * @param socketChannel
     * @throws IOException
     */
    public void setup(SocketChannel socketChannel) throws IOException {
        this.channel = socketChannel;
        // 拿到单例
        IoContext context = IoContext.get();
        SocketChannelAdapter adapter = new SocketChannelAdapter(channel, context.getIoProvider(), this);
        //发送和接收
        this.sender = adapter;
        this.receiver = adapter;
        // 开始读取数据
        // readNextMessage();
        sendDispatcher = new AsyncSendDispatcher(sender);
        receiveDispatcher = new AsyncReceiveDispatcher(receiver, receivePacketCallback);
        // 启动接收
        receiveDispatcher.start();
    }

    public void send(String msg) {
        SendPacket packet = new StringSendPacket(msg);
        sendDispatcher.send(packet);
    }

    /**
     * 关闭操作
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        receiveDispatcher.close();;
        sendDispatcher.close();
        sender.close();
        receiver.close();
        channel.close();
    }

    @Override
    public void onChannelClosed(SocketChannel channel) {

    }

    /**
     * 用来做监听的，监听IO Args的状态
     * 要将异步接收的数据打印出来
    private IoArgs.IoArgsEventListener echoReceiveListener = new IoArgs.IoArgsEventListener() {
        // 开始接收数据
        @Override
        public void onStarted(IoArgs args) {
            System.out.println();
        }

        // 结束接收数据
        @Override
        public void onCompleted(IoArgs args) {
            // 打印
            onReceiveNewMessage(args.bufferString());
            // 读取下一条数据
            readNextMessage();
        }
    };
    */

    /**
     * 接收到数据的回调
     */
    private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = packet -> {
        if (packet instanceof StringReceivePacket) {
            String msg = ((StringReceivePacket) packet).string();
            onReceiveNewMessage(msg);
        }
    };

    /**
     * 当有新数据时回调
     * @param str
     */
    protected void onReceiveNewMessage(String str) {
        System.out.println(key.toString() + ":" + str);
    }
}
