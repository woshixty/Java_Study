package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.ReceiveDispatcher;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 接收调度
 */
public class AsyncReceiveDispatcher implements ReceiveDispatcher, IoArgs.IoArgsEventProcessor, AsyncPacketWriter.PacketProvider {
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 接收者
    private final Receiver receiver;
    // 接收回调定义
    private final ReceivePacketCallback callback;

    private final AsyncPacketWriter writer = new AsyncPacketWriter(this);

    public AsyncReceiveDispatcher(Receiver receiver, ReceivePacketCallback callback) {
        this.receiver = receiver;
        this.receiver.setReceiveListener(this);
        this.callback = callback;
    }

    /**
     * 开始进入接收方法
     */
    @Override
    public void start() {
        registerReceive();
    }

    /**
     * 停止接收数据
     */
    @Override
    public void stop() {

    }

    /**
     * 关闭操作，关闭相关流
     */
    @Override
    public void close() {
        if (isClosed.compareAndSet(false, true)) {
            writer.close();
        }
    }

    /**
     * 自主发起的关闭操作，并且需要进行通知
     */
    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    /**
     * 注册接收数据
     */
    private void registerReceive() {
        try {
            receiver.postReceiveAsync();
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    /**
     * 网络接收就绪，此时可以读取数据，需要返回一个容器用于容纳数据
     *
     * @return 用以容纳数据的IoArgs
     */
    @Override
    public IoArgs provideIoArgs() {
        return writer.takeIoArgs();
    }

    /**
     * 接收数据失败
     *
     * @param args
     * @param e
     */
    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
    }

    /**
     * 接收数据成功
     *
     * @param args IoArgs
     */
    @Override
    public void onConsumeCompleted(IoArgs args) {
        // 有数据则重复消费
        do {
            writer.consumeIoArgs(args);
        } while (args.remained());
        registerReceive();
    }

    /**
     * 构建Packet操作，根据类型、长度构建一份用于接收数据的Packet
     *
     * @param type       Packet类型
     * @param length     Packet长度
     * @param headerInfo Packet headerInfo
     * @return
     */
    @Override
    public ReceivePacket takePacket(byte type, long length, byte[] headerInfo) {
        return callback.onArrivedNewPacket(type, length);
    }

    /**
     * 当Packet接收数据完成或终止时回调
     *
     * @param packet    接收包
     * @param isSucceed 是否成功接收完成
     */
    @Override
    public void completedPacket(ReceivePacket packet, boolean isSucceed) {
        CloseUtils.close(packet);
        callback.onReceivePacketCompleted(packet);
    }
}
