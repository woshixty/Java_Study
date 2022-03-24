package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 发送者
 */
public interface Sender extends Closeable {
    /**
     * 单独set发送者回调
     * @param processor
     */
    void setSendListener(IoArgs.IoArgsEventProcessor processor);

    /**
     * 异步发送数据
     * @return
     * @throws IOException
     */
    boolean postSendAsync() throws IOException;

    /**
     * 获取最后一次写数据的时间点
     * @return
     */
    long getLastWriterTime();
}
