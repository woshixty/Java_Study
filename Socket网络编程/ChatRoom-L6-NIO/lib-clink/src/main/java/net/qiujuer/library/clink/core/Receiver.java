package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

public interface Receiver extends Closeable {
    /**
     * 设置接收的监听
     * @param listener
     * @return
     */
    void setReceiveListener(IoArgs.IoArgsEventListener listener);

    boolean receiveAsync(IoArgs args) throws IOException;
}
