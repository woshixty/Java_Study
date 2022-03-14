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
    private IoArgs.IoArgsEventListener receiveIoEventListener;
    private IoArgs.IoArgsEventListener sendIoEventListener;

    public SocketChannelAdapter(SocketChannel channel, IoProvider ioProvider,
                                OnChannelStatusChangedListener listener) throws IOException {
        this.channel = channel;
        this.ioProvider = ioProvider;
        this.listener = listener;
        // 配置为非阻塞
        channel.configureBlocking(false);
    }

    /**
     * 异步接收
     * @param listener  接收状态回调
     * @return
     * @throws IOException
     */
    @Override
    public boolean receiveAsync(IoArgs.IoArgsEventListener listener) throws IOException {
        // 如果当前的channel已经关闭
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        // 对于当前接收的回调
        receiveIoEventListener = listener;
        // 返回当前的注册是否成功
        return ioProvider.registerInput(channel, inputCallback);
    }

    /**
     * 异步发送
     * @param args  要发送的数据
     * @param listener  发送的状态回调
     * @return
     * @throws IOException
     */
    @Override
    public boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException {
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        sendIoEventListener = listener;
        // 当前发送的数据附加到回调中
        outputCallback.setAttach(args);
        return ioProvider.registerOutput(channel, outputCallback);
    }

    /**
     * 关闭操作-原子操作
     */
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

    /**
     * 当有数据可读时进行回调这个方法
     */
    private final IoProvider.HandleInputCallback inputCallback = new IoProvider.HandleInputCallback() {
        @Override
        protected void canProviderInput() {
            // 判断连接是否关闭
            if (isClosed.get()) {
                return;
            }
            // 直接 new IoArgs()
            IoArgs args = new IoArgs();
            IoArgs.IoArgsEventListener listener = SocketChannelAdapter.this.receiveIoEventListener;
            if (listener != null) {
                listener.onStarted(args);
            }
            try {
                // 具体的读取操作
                if (args.readFrom(channel) > 0 && listener != null) {
                    // 读取完成回调
                    listener.onCompleted(args);
                } else {
                    throw new IOException("Cannot readFrom any data!");
                }
            } catch (IOException ignored) {
                CloseUtils.close(SocketChannelAdapter.this);
            }
        }
    };

    /**
     * 输出部分
     */
    private final IoProvider.HandleOutputCallback outputCallback = new IoProvider.HandleOutputCallback() {
        @Override
        protected void canProviderOutput(Object attach) {
            if (isClosed.get()) {
                return;
            }
            // TODO
            sendIoEventListener.onCompleted(null);
        }
    };

    /**
     * SocketChannel关闭的回调接口
     */
    public interface OnChannelStatusChangedListener {
        void onChannelClosed(SocketChannel channel);
    }
}
