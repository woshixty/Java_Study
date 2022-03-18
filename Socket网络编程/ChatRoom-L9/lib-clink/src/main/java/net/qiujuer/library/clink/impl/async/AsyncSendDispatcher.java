package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.*;
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
public class AsyncSendDispatcher implements SendDispatcher, IoArgs.IoArgsEventProcessor, AsyncPacketReader.PacketProvider {
    private final Sender sender;
    // 线程安全的集合框架
    private final Queue<SendPacket> queue = new ConcurrentLinkedQueue<>();
    // 处于一个传输过程中
    private final AtomicBoolean isSending = new AtomicBoolean();
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    private final AsyncPacketReader reader = new AsyncPacketReader(this);
    // 同步关键字
    private final Object queueLock = new Object();

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
        synchronized (queueLock) {
            queue.offer(packet);
            // 当前不是发送状态
            if (isSending.compareAndSet(false, true)) {
                if (reader.requestTakePacket()) {
                    // 请求发送
                    requestSend();
                }
            }
        }
    }

    /**
     * 取消发送
     * @param packet
     */
    @Override
    public void cancel(SendPacket packet) {
        // 从队列中移除
        boolean ret;
        synchronized (queueLock) {
            ret = queue.remove(packet);
        }
        if (ret == true) {
            packet.cancel();
            return;
        }
        reader.cancel(packet);
    }

    /**
     * 拿发送包
     * @return
     */
    @Override
    public SendPacket takePacket() {
        SendPacket packet;
        synchronized (queueLock) {
            packet = queue.poll();
            if (packet == null) {
                isSending.set(false);
                return null;
            }
        }
        if (packet.isCanceled()) {
            // 已取消，不用发送
            return takePacket();
        }
        return packet;
    }

    /**
     * 完成packet包发送
     * @param packet    发送包
     * @param isSucceed 是否成功发送完成
     */
    @Override
    public void completedPacket(SendPacket packet, boolean isSucceed) {
        CloseUtils.close(packet);
    }

    /**
     * 请求网络进行数据发送
     */
    private void requestSend() {
        try {
            // 注册发送下一条数据的操作
            sender.postSendAsync();
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    /**
     * 关闭自己并通知出去
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
            // reader的关闭操作
            reader.close();
        }
    }

    /**
     * 提供IoArgs
     * @return
     */
    @Override
    public IoArgs provideIoArgs() {
        return reader.fillData();
    }

    /**
     * 消费数据失败
     * @param args
     * @param e
     */
    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        if (args != null) {
            e.printStackTrace();
        } else {
            // TODO: 2022/3/17
        }
    }

    /**
     * 消费完全
     * @param args
     */
    @Override
    public void onConsumeCompleted(IoArgs args) {
        // 继续发送当前包
        if (reader.requestTakePacket()) {
            requestSend();
        }
    }
}
