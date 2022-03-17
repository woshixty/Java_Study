package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendDispatcher;
import net.qiujuer.library.clink.core.SendPacket;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  异步发送的实现类
 */
public class AsyncSendDispatcher implements SendDispatcher, IoArgs.IoArgsEventProcessor {
    private final Sender sender;
    // 线程安全的集合框架
    private final Queue<SendPacket> queue = new ConcurrentLinkedQueue<>();
    // 处于一个传输过程中
    private final AtomicBoolean isSending = new AtomicBoolean();
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // IO输入和输出的参数类
    private IoArgs ioArgs = new IoArgs();
    private SendPacket<?> packetTemp;
    // 当前发送packet的大小与进度
    private ReadableByteChannel packetChannel;
    private long total;
    private long position;

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
        sender.setSendListener(this);
    }

    /**
     * 发送包
     * @param packet
     */
    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);
        // 当前不是发送状态
        if (isSending.compareAndSet(false, true)) {
            sendNextPacket();
        }
    }

    /**
     * 拿发送包
     * @return
     */
    private SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet != null && packet.isCanceled()) {
            // 已取消，不用发送
            return takePacket();
        }
        return packet;
    }

    /**
     * 发送下一个包
     */
    private void sendNextPacket() {
        SendPacket temp = packetTemp;
        if (temp != null) {
            CloseUtils.close(temp);
        }
        SendPacket packet = packetTemp = takePacket();
        // 队列为空了，就代表当前没有发送包要发送了
        if (packet == null) {
            isSending.set(false);
            return;
        }
        // 发送数据
        total = packet.length();
        position = 0;
        sendCurrentPacket();
    }

    /**
     * 发送当前包
     */
    private void sendCurrentPacket() {
        if (position >= total) {
            completePacket(position == total);
            sendNextPacket();
            return;
        }
        // 如果没有发送完全
        try {
            // 注册发送下一条数据的操作
            sender.postSendAsync();
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    /**
     * 完成packet发送
     * @param isSucceed  是否成功
     */
    private void completePacket(boolean isSucceed) {
        SendPacket packet = this.packetTemp;
        if (packet == null)
            return;
        CloseUtils.close(packet);
        CloseUtils.close(packetChannel);
        packetTemp = null;
        packetChannel = null;
        total = 0;
        position = 0;
    }

    /**
     * 关闭并通知出去
     */
    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    /**
     * Closeable类的关闭方法
     * @throws IOException
     */
    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            // 设置为没有发送状态
            isSending.set(false);
            // 异常关闭导致的完成的操作
            completePacket(false);
        }
    }

    @Override
    public void cancel(SendPacket packet) {

    }

    /**
     * 提供IoArgs
     * @return
     */
    @Override
    public IoArgs provideIoArgs() {
        IoArgs args = this.ioArgs;
        // 为首包
        if (packetChannel == null) {
            packetChannel = Channels.newChannel(packetTemp.open());
            args.limit(4);
            // args.writeLength((int) packetTemp.length());
        } else {
            args.limit((int) Math.min(args.capacity(), total - position));
            try {
                int count = args.readFrom(packetChannel);
                position += count;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        return args;
    }

    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConsumeCompleted(IoArgs args) {
        // 继续发送当前包
        sendCurrentPacket();
    }
}
