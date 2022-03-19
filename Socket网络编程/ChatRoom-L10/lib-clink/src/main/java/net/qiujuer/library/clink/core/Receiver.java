package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 接收者
 */
public interface Receiver extends Closeable {
    /**
     * 单独set接收者回调
     * @param processor
     */
    void setReceiveListener(IoArgs.IoArgsEventProcessor processor);

    /**
     * 异步接收数据接收
     * IosArgs需要进行一定的参数设置，例如每次读取多大
     * @return
     * @throws IOException
     */
    boolean postReceiveAsync() throws IOException;
}
