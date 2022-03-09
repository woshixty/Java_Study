package net.qiujuer.library.clink.core;

import java.io.Closeable;
import java.io.IOException;

/**
 * 发送者
 */
public interface Sender extends Closeable {
    /**
     * 异步发送数据
     * @param args  要发送的数据
     * @param listener  发送的状态回调
     * @return
     * @throws IOException
     */
    boolean sendAsync(IoArgs args, IoArgs.IoArgsEventListener listener) throws IOException;
}
