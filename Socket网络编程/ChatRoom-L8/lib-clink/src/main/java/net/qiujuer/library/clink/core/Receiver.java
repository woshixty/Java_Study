package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 接收者
 */
public interface Receiver extends Closeable {
    /**
     * 单独set监听者的操作
     * @param listener
     * @return
     */
    void setReceiveListener(IoArgs.IoArgsEventListener listener);

    /**
     * 异步接收数据接收
     * IosArgs需要进行一定的参数设置，例如每次读取多大
     * @param args
     * @return
     * @throws IOException
     */
    boolean receiveAsync(IoArgs args) throws IOException;
}
