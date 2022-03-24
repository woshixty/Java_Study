package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.IoProvider;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *  发送与接收的具体实现
 *  发送与接受的职责
 */
public class SocketChannelAdapter implements Sender, Receiver, Cloneable {
    // channel的关闭标志
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    // 面向流的连接套接字的可选通道--发送承载者
    private final SocketChannel channel;
    // 提供读写事件注册
    private final IoProvider ioProvider;
    // 将channel的异常回调回去
    private final OnChannelStatusChangedListener listener;
    // 对于当前的发送与接收的回调
    private IoArgs.IoArgsEventProcessor receiveIoEventProcessor;
    private IoArgs.IoArgsEventProcessor sendIoEventProcessor;
    // 时间点
    private volatile long lastReadTime = System.currentTimeMillis();
    private volatile long lastWriteTime = System.currentTimeMillis();

    public SocketChannelAdapter(SocketChannel channel, IoProvider ioProvider,
                                OnChannelStatusChangedListener listener) throws IOException {
        this.channel = channel;
        this.ioProvider = ioProvider;
        this.listener = listener;
        // 配置为非阻塞
        channel.configureBlocking(false);
    }

    @Override
    public void setReceiveListener(IoArgs.IoArgsEventProcessor processor) {
        receiveIoEventProcessor = processor;
    }

    @Override
    public boolean postReceiveAsync() throws IOException, IllegalAccessException {
        // 如果当前的channel已经关闭
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        // 进行CallBack状态监测，判断是否处于自循环状态
        try {
            inputCallback.checkAttachNull();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw e;
        }
        // 返回当前的注册是否成功
        return ioProvider.registerInput(channel, inputCallback);
    }

    @Override
    public long getLastReadTime() {
        return lastReadTime;
    }

    @Override
    public long getLastWriterTime() {
        return lastWriteTime;
    }

    @Override
    public void setSendListener(IoArgs.IoArgsEventProcessor processor) {
        sendIoEventProcessor = processor;
    }

    @Override
    public boolean postSendAsync() throws IOException {
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        // 当前发送的数据附加到回调中
        return ioProvider.registerOutput(channel, outputCallback);
    }

    @Override
    public void close() {
        // 原子操作
        // 比较当前状态是否为false，再将其更新为true
        if (isClosed.compareAndSet(false, true)) {
            // 解除注册回调
            ioProvider.unRegisterInput(channel);
            ioProvider.unRegisterOutput(channel);
            // 关闭
            CloseUtils.close(channel);
            // 回调当前Channel已关闭
            listener.onChannelClosed(channel);
        }
    }

    private final IoProvider.HandleProviderCallback inputCallback = new IoProvider.HandleProviderCallback() {
        @Override
        protected void onProviderIo(IoArgs args) {
            // 判断连接是否关闭
            if (isClosed.get()) {
                return;
            }
            lastReadTime = System.currentTimeMillis();
            IoArgs.IoArgsEventProcessor processor = receiveIoEventProcessor;
            if (args == null) {
                args = processor.provideIoArgs();
            }
            try {
                // 具体的读取操作
                if (args == null) {
                    processor.onConsumeFailed(null, new IOException("ProvideIoArgs is null."));
                } else {
                    int count = args.readFrom(channel);
                    if (count == 0) {
                        // 当前无法发送数据
                        System.out.println("Current read zero data");
                    }
                    if (args.remained()) {
                        attach = args;
                        // 读取完成回调
                        ioProvider.registerInput(channel, this);
                    } else {
                        attach = null;
                        processor.onConsumeCompleted(args);
                    }
                }
            } catch (IOException ignored) {
                CloseUtils.close(SocketChannelAdapter.this);
            }
        }
    };

    /**
     * 当有数据可写时进行回调这个方法
     */
    private final IoProvider.HandleProviderCallback outputCallback = new IoProvider.HandleProviderCallback() {
        @Override
        protected void onProviderIo(IoArgs args) {
            // 判断是否为为空
            if (isClosed.get()) {
                return;
            }
            lastWriteTime = System.currentTimeMillis();
            IoArgs.IoArgsEventProcessor processor = sendIoEventProcessor;
            if (args == null) {
                // 拿一份新的
                args = processor.provideIoArgs();
            }
            try {
                // 具体的读取操作
                if (args == null) {
                    processor.onConsumeFailed(null, new IOException("ProvideIoArgs is null."));
                }
                int count = args.writeTo(channel);
                if (count == 0) {
                    // 当前无法发送数据
                    System.out.println("Current write zero data");
                }
                if (args.remained()) {
                    // 附加当前未消费完的args
                    attach = args;
                    // 再次注册
                    ioProvider.registerOutput(channel, this);
                } else {
                    // 置为null
                    attach = null;
                    // 输出完成回调
                    processor.onConsumeCompleted(args);
                }
            } catch (IOException ignored) {
                CloseUtils.close(SocketChannelAdapter.this);
            }
        }
    };

    /**
     * SocketChannel关闭的回调接口
     */
    public interface OnChannelStatusChangedListener {
        void onChannelClosed(SocketChannel channel);
    }
}
