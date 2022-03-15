package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.ReceiveDispatcher;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.Channels;
import java.nio.channels.WritableByteChannel;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncReceiveDispatcher implements ReceiveDispatcher, IoArgs.IoArgsEventProcessor {
    // 是否已关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 接收者
    private final Receiver receiver;
    // 接收回调定义
    private final ReceivePacketCallback callback;
    private IoArgs ioArgs = new IoArgs();
    private ReceivePacket<?> packetTemp;
    private WritableByteChannel packetChannel;
    private long total;
    private long position;

    public AsyncReceiveDispatcher(Receiver receiver, ReceivePacketCallback callback) {
        this.receiver = receiver;
        this.receiver.setReceiveListener(this);
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
            completePacket(false);
            // 不需要
//            ReceivePacket packet = this.packetTemp;
//            if (packet != null) {
//                packetTemp = null;
//                CloseUtils.close(packet);
//            }
        }
    }

    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    /**
     * 完成数据接收操作
     */
    private void completePacket(boolean isSucceed) {
        ReceivePacket packet = this.packetTemp;
        CloseUtils.close(packet);
        packetTemp = null;

        WritableByteChannel channel = this.packetChannel;
        CloseUtils.close(channel);
        packetChannel = null;

        if (packet != null) {
            callback.onReceivePacketCompleted(packet);
        }
    }

    /**
     * 真正的接收操作
     */
    private void registerReceive() {
        try {
            receiver.postReceiveAsync();
        } catch (IOException e) {
            closeAndNotify();
        }
    }

    /**
     * 解析数据到packet
     * @param args
     */
    private void assemblePacket(IoArgs args) {
        if (packetTemp == null) {
            int length = args.readLength();
            packetTemp = new StringReceivePacket(length);
            packetChannel = Channels.newChannel(packetTemp.open());
            total = length;
            position = 0;
        }
        try {
            // 数据写失败了直接报异常
            int count = args.writeTo(packetChannel);
            position += count;
            // 检查是否已完成一份packet接收
            if (position == total) {
                completePacket(true);
                packetTemp = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
            completePacket(false);
        }
    }

    @Override
    public IoArgs provideIoArgs() {
        IoArgs args = ioArgs;
        // 接收数据的大小
        int receiveSize;
        if (packetTemp == null) {
            receiveSize = 4;
        } else {
            receiveSize = (int) Math.min(total - position, args.capacity());
        }
        // 设置本次接收数据大小
        args.limit(receiveSize);
        return args;
    }

    @Override
    public void onConsumeFailed(IoArgs args, Exception e) {
        e.printStackTrace();
    }

    @Override
    public void onConsumeCompleted(IoArgs args) {
        assemblePacket(args);
        registerReceive();
    }
}
