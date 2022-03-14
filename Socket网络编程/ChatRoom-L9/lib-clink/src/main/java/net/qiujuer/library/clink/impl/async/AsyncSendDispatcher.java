package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendDispatcher;
import net.qiujuer.library.clink.core.SendPacket;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  异步发送的实现类
 */
public class AsyncSendDispatcher implements SendDispatcher {
    private final Sender sender;
    // 线程安全的集合框架
    private final Queue<SendPacket> queue = new ConcurrentLinkedQueue<>();
    // 处于一个传输过程中
    private final AtomicBoolean isSending = new AtomicBoolean();
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    private IoArgs ioArgs = new IoArgs();
    private SendPacket packetTemp;

    // 当前发送packet的大小与进度
    private int total;
    private int position;

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
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
            CloseUtils.close(packetTemp);
        }
        SendPacket packet = takePacket();
        // 队列为空了，就代表当前没有发送包要发送了
        if (packet == null) {
            isSending.set(false);
            return;
        }
        // 发送数据
        total = (int) packet.length();
        position = 0;
        sendCurrentPacket();
    }

    /**
     * 发送当前包
     */
    private void sendCurrentPacket() {
        IoArgs args = this.ioArgs;
        // 开始清理
        args.startWriting();
        if (position >= total) {
            sendNextPacket();
            return;
        } else if (position == 0) {
            // 刚刚开始发送
            // 首包，需要携带长度信息
            args.writeLength(total);
        }

        byte[] bytes = packetTemp.open();
        int count = args.readFrom(bytes, position);
        position += count;

        // 完成封装
        args.finishWriting();

        try {
            sender.sendAsync(args, ioArgsEventListener);
        } catch (IOException e) {
            closeAndNotify();
        }
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
            SendPacket packet = this.packetTemp;
            if (packet != null) {
                packetTemp = null;
                CloseUtils.close(packet);
            }
        }
    }

    /**
     * 进度的回调
     */
    private final IoArgs.IoArgsEventListener ioArgsEventListener = new IoArgs.IoArgsEventListener() {
        @Override
        public void onStarted(IoArgs args) {

        }

        @Override
        public void onCompleted(IoArgs args) {
            // 继续发送当前包
            sendCurrentPacket();
        }
    };

    @Override
    public void cancel(SendPacket packet) {

    }
}
