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
    boolean registerInput(SocketChannel channel, HandleInputCallback callback);

    /**
     * 同注册输入
     * @param channel
     * @param callback
     * @return
     */
    boolean registerOutput(SocketChannel channel, HandleOutputCallback callback);

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
     * 当有数据可读时进行回调
     */
    abstract class HandleInputCallback implements Runnable {
        @Override
        public final void run() {
            canProviderInput();
        }

        /**
         * 可以从SocketChannel读取数据
         */
        protected abstract void canProviderInput();
    }

    /**
     * 与上述读相比
     * 添加了一个附件
     */
    abstract class HandleOutputCallback implements Runnable {
        // 把要发送的数据作为attach
        private Object attach;

        @Override
        public final void run() {
            canProviderOutput(attach);
        }

        public final void setAttach(Object attach) {
            this.attach = attach;
        }

        protected abstract void canProviderOutput(Object attach);
    }

}
