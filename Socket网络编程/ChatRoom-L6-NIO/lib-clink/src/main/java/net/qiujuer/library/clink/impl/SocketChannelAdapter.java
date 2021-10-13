package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.IoProvider;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.atomic.AtomicBoolean;

public class SocketChannelAdapter implements Sender, Receiver, Cloneable {
    //表示是否已被关闭
    private final AtomicBoolean isClosed = new AtomicBoolean(false);
    //发送承载者
    private final SocketChannel channel;
    private final IoProvider ioProvider;
    //传入回调的对象
    private final OnChannelStatusChangedListener listener;
    //发送与接收的回调
    private IoArgs.IoArgsEventListener receiveIoEventListener;
    private IoArgs.IoArgsEventListener sendIoEventListener;

    public SocketChannelAdapter(SocketChannel channel, IoProvider ioProvider,
                                OnChannelStatusChangedListener listener) throws IOException {
        this.channel = channel;
        this.ioProvider = ioProvider;
        this.listener = listener;
        //配置当前是否阻塞
        channel.configureBlocking(false);
    }

    //接收
    @Override
    public boolean receiveAsync(IoArgs.IoArgsEventListener listener) throws IOException {
        //channel已经关闭
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        //赋值
        receiveIoEventListener = listener;
        //看看接收是否成功
        return ioProvider.registerInput(channel, inputCallback);
    }

    @Override
    public boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException {
        //channel已经关闭
        if (isClosed.get()) {
            throw new IOException("Current channel is closed!");
        }
        sendIoEventListener = listener;
        //当前发送的数据附加到回调中
        outputCallback.setAttach(args);
        return ioProvider.registerOutput(channel, outputCallback);
    }

    @Override
    public void close() {
        //比较当前内部状态是否为false，否则更新为true
        if (isClosed.compareAndSet(false, true)) {
            //解除注册回调
            ioProvider.unRegisterInput(channel);
            ioProvider.unRegisterOutput(channel);
            //关闭
            CloseUtils.close(channel);
            //回调当前Channel已关闭
            listener.onChannelClosed(channel);
        }
    }

    private final IoProvider.HandleInputCallback inputCallback = new IoProvider.HandleInputCallback() {
        @Override
        protected void canProviderInput() {
            if (isClosed.get()) {
                return;
            }
            IoArgs args = new IoArgs();
            IoArgs.IoArgsEventListener listener = SocketChannelAdapter.this.receiveIoEventListener;
            if (listener != null) {
                listener.onStarted(args);
            }
            try {
                //具体的读取操作
                if (args.read(channel) > 0 && listener != null) {
                    //读取完成回调
                    listener.onCompleted(args);
                } else {
                    throw new IOException("Cannot read any data!");
                }
            } catch (IOException ignored) {
                CloseUtils.close(SocketChannelAdapter.this);
            }
        }
    };

    private final IoProvider.HandleOutputCallback outputCallback = new IoProvider.HandleOutputCallback() {
        @Override
        protected void canProviderOutput(Object attach) {

        }
    };

    private final IoProvider.HandleInputCallback inputCallBack = new IoProvider.HandleInputCallback() {
        @Override
        protected void canProviderInput() {

        }
    };

    //发生异常回调出去
    public interface OnChannelStatusChangedListener {
        void onChannelClosed(SocketChannel channel);
    }
}
