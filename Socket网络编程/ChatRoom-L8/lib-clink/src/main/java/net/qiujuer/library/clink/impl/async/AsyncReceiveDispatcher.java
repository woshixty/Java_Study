package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.ReceiveDispatcher;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncReceiveDispatcher implements ReceiveDispatcher {
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 接收者
    private final Receiver receiver;
    // 接收回调定义
    private final ReceivePacketCallback callback;

    private IoArgs ioArgs = new IoArgs();
    private ReceivePacket packetTemp;
    private byte[] buffer;
    private int total;
    private int position;

    public AsyncReceiveDispatcher(Receiver receiver, ReceivePacketCallback callback) {
        this.receiver = receiver;
        this.receiver.setReceiveListener(ioArgsEventListener);
        this.callback = callback;
    }

    /**
     * 开始接收操作
     */
    @Override
    public void start() {
        registerReceive();
    }

    @Override
    public void stop() {

    }

    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            ReceivePacket packet = this.packetTemp;
            if (packet != null) {
                packetTemp = null;
                CloseUtils.close(packet);
            }
        }
    }

    /**
     * 真正的接收操作
     */
    private void registerReceive() {
        try {
            receiver.receiveAsync(ioArgs);
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    /**
     * 完成数据接收操作
     */
    private void completePacket() {
        ReceivePacket packet = this.packetTemp;
        CloseUtils.close(packet);
        packetTemp = null;
        callback.onReceivePacketCompleted(packet);
    }

    /**
     * 回调
     */
    private IoArgs.IoArgsEventListener ioArgsEventListener = new IoArgs.IoArgsEventListener() {
        @Override
        public void onStarted(IoArgs args) {
            int receiveSize;
            if (packetTemp == null) {
                // 接收长度
                receiveSize = 4;
            } else {
                receiveSize = Math.min(total - position, args.capacity());
            }
            // 设置本次接收数据大小
            args.limit(receiveSize);
        }

        @Override
        public void onCompleted(IoArgs args) {
            // 解析数据
            assemblePacket(args);
            // 继续接收下一条数据
            registerReceive();
        }
    };

    /**
     * 解析数据到packet
     * @param args
     */
    private void assemblePacket(IoArgs args) {
        if (packetTemp == null) {
            // 读取数据字节长度
            int length = args.readLength();
            packetTemp = new StringReceivePacket(length);
            buffer = new byte[length];
            total = length;
            position = 0;
        }
        // 开始读取
        // 写入数据到buffer中
        int count = args.writeTo(buffer, 0);
        // 有数据写出来
        if (count > 0) {
            // 将buffer复制到packetTemp的buffer中
            packetTemp.save(buffer, count);
            position += count;
            // 检查是否已完成一份packet接收
            if (position == total) {
                completePacket();
            }
        }
    }
}
