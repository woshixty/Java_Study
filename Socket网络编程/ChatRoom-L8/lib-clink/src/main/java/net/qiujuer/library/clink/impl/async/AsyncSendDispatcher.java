package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendDispatcher;
import net.qiujuer.library.clink.core.SendPacket;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

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

    private IoArgs ioArgs = new IoArgs();
    private SendPacket packetTemp;

    // 当前发送packet的大小与进度
    private int total;
    private int position;

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);
        // 当前不是发送状态
        if (isSending.compareAndSet(false, true)) {
            sendNextPacket();
        }
    }

    private SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet != null && packet.isCanceled()) {
            // 已取消，不用发送
            return takePacket();
        }
        return packet;
    }

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
        total = packet.length();
        position = 0;
        sendCurrentPacket();
    }

    private void sendCurrentPacket() {
        IoArgs ioArgs = this.ioArgs;
    }

    @Override
    public void cancel(SendPacket packet) {

    }
}
