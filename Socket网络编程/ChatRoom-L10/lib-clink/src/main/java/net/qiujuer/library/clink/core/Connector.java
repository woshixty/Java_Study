package net.qiujuer.library.clink.core;

import net.qiujuer.library.clink.box.BytesReceivePacket;
import net.qiujuer.library.clink.box.FileReceivePacket;
import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.box.StringSendPacket;
import net.qiujuer.library.clink.impl.SocketChannelAdapter;
import net.qiujuer.library.clink.impl.async.AsyncReceiveDispatcher;
import net.qiujuer.library.clink.impl.async.AsyncSendDispatcher;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.UUID;

/**
 * 连接
 * 当关闭的时候的回调：SocketChannelAdapter.OnChannelStatusChangedListener
 */
public abstract class Connector implements Closeable, SocketChannelAdapter.OnChannelStatusChangedListener {
    // 代表这个连接的唯一性
    protected UUID key = UUID.randomUUID();
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

    /**
     * 发送字符串
     * @param msg
     */
    public void send(String msg) {
        SendPacket packet = new StringSendPacket(msg);
        sendDispatcher.send(packet);
    }

    /**
     * 发送文件
     * @param packet
     */
    public void send(SendPacket packet) {
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

    /**
     * 当SocketChannel关闭时
     * @param channel
     */
    @Override
    public void onChannelClosed(SocketChannel channel) {

    }

    /**
     * 当有新数据包时回调
     * @param packet
     */
    protected void onReceivedPacket(ReceivePacket packet) {
        // TODO: 2022/3/23 注释掉下面的
        System.out.println(key.toString() + ": [NEW Packet] Type:" + packet.type() + ", Length:" + packet.length);
    }

    /**
     * 创建一个临时的文件
     * @return
     */
    protected abstract File createNewReceiveFile();

    /**
     * 接收到数据的回调
     */
    private ReceiveDispatcher.ReceivePacketCallback receivePacketCallback = new ReceiveDispatcher.ReceivePacketCallback() {
        @Override
        public ReceivePacket<?, ?> onArrivedNewPacket(byte type, long length) {
            switch (type) {
                case Packet.TYPE_MEMORY_BYTES:
                case Packet.TYPE_STREAM_DIRECT:
                    return new BytesReceivePacket(length);
                case Packet.TYPE_MEMORY_STRING:
                    return new StringReceivePacket(length);
                case Packet.TYPE_STREAM_FILE:
                    return new FileReceivePacket(length, createNewReceiveFile());
                default:
                    throw new UnsupportedOperationException("Unsupported packet type:" + type);
            }
        }

        @Override
        public void onReceivePacketCompleted(ReceivePacket packet) {
            onReceivedPacket(packet);
        }
    };
}
