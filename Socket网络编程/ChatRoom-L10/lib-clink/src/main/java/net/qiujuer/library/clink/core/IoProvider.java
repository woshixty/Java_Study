package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.nio.channels.SocketChannel;

/**
 * 提供者的模式
 */
public interface IoProvider extends Closeable {
    /**
     * 注册一个输入，想要从SocketChannel取得数据
     * 观察者模式
     * 监测SocketChannel的可读状态，通过HandleInputCallback回调
     * @param channel
     * @param callback
     * @return
     */
    boolean registerInput(SocketChannel channel, HandleProviderCallback callback);

    /**
     * 同注册输入
     * @param channel
     * @param callback
     * @return
     */
    boolean registerOutput(SocketChannel channel, HandleProviderCallback callback);

    /**
     * 解绑
     * @param channel
     */
    void unRegisterInput(SocketChannel channel);

    /**
     * 解绑
     * @param channel
     */
    void unRegisterOutput(SocketChannel channel);

    /**
     * 与上述读相比
     * 添加了一个附件
     */
    abstract class HandleProviderCallback implements Runnable {
        protected volatile IoArgs attach;

        @Override
        public final void run() {
            onProviderIo(attach);
        }

        protected abstract void onProviderIo(IoArgs args);

        public void checkAttachNull() throws IllegalAccessException {
            if (attach != null) {
                throw new IllegalAccessException("Current attach is not empty");
            }
        }
    }

}
