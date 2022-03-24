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
 *  发送调度
 */
public class AsyncSendDispatcher implements SendDispatcher, IoArgs.IoArgsEventProcessor, AsyncPacketReader.PacketProvider {
    private final Sender sender;
    // 线程安全的集合框架
    private final Queue<SendPacket> queue = new ConcurrentLinkedQueue<>();
    // 处于一个传输过程中
    private final AtomicBoolean isSending = new AtomicBoolean();
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 异步读
    private final AsyncPacketReader reader = new AsyncPacketReader(this);

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
        sender.setSendListener(this);
    }

    /**
     * 发送Packet
     * 首先添加到队列，如果当前状态为未启动发送状态
     * 则，尝试让reader提取一份packet进行数据发送
     * <p>
     * 如果提取数据后reader有数据，则进行异步输出注册
     *
     * @param packet 数据
     */
    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);
        requestSend();
    }

    /**
     * 发送心跳帧
     */
    @Override
    public void sendHeartbeat() {
        if (queue.size() > 0)
            return;
        if (reader.requestSendHeartbeatFrame()) {
            requestSend();
        }
    }

    /**
     * 取消Packet操作
     * 如果还在队列中，代表Packet未进行发送，则直接标志取消，并返回即可
     * 如果未在队列中，则让reader尝试扫描当前发送序列，查询是否当前Packet正在发送
     * 如果是则进行取消相关操作
     *
     * @param packet 数据
     */
    @Override
    public void cancel(SendPacket packet) {
        boolean ret = queue.remove(packet);
        if (ret) {
            packet.cancel();
            return;
        }
        reader.cancel(packet);
    }

    /**
     * reader从当前队列中提取一份Packet
     *
     * @return 如果队列有可用于发送的数据则返回该Packet
     */
    @Override
    public SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet == null)
            return null;
        // 已取消，不用发送
        if (packet.isCanceled())
            return takePacket();
        return packet;
    }

    /**
     * 完成Packet发送
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
        synchronized (isSending) {
            if (isSending.get() || isClosed.get()) {
                return;
            }
            // 返回true代表当前有数据要发送
            if (reader.requestTakePacket()) {
                try {
                    // 注册发送下一条数据的操作
                    boolean isSucceed = sender.postSendAsync();
                    if (isSucceed) {
                        isSending.set(true);
                    }
                } catch (IOException e) {
                    closeAndNotify();
                }
            }
        }
    }

    /**
     * 关闭自己并通知出去
     */
    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    /**
     * 关闭操作，关闭自己的同时关闭reader
     */
    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            // reader的关闭操作
            reader.close();
            // 队列清空
            queue.clear();
            // 设置为没有发送状态
            synchronized (isSending) {
                isSending.set(false);
            }
        }
    }

    /**
     * 网络发送就绪回调，当前已进入发送就绪状态，等待填充数据进行发送
     * 此时从reader中填充数据，并进行后续网络发送
     *
     * @return NULL，可能填充异常，或者想要取消本次发送
     */
    @Override
    public IoArgs provideIoArgs() {
        return isClosed.get() ? null : reader.fillData();
    }

    /**
     * 网络发送IoArgs出现异常
     *
     * @param args IoArgs
     * @param e    异常信息
     */
    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
        synchronized (isSending) {
            isSending.set(false);
        }
        // 继续请求发送当前的数据
        requestSend();
    }

    /**
     * 网络发送IoArgs完成回调
     * 在该方法进行reader对当前队列的Packet提取，并进行后续的数据发送注册
     *
     * @param args IoArgs
     */
    @Override
    public void onConsumeCompleted(IoArgs args) {
        synchronized (isSending) {
            isSending.set(false);
        }
        // 继续请求发送当前的数据
        requestSend();
    }
}
