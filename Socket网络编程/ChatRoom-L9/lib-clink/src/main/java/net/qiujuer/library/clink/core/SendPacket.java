package net.qiujuer.library.clink.core;

import java.io.InputStream;

/**
 * 发送包的定义
 */
public abstract class SendPacket<T extends InputStream> extends Packet<T> {
    private boolean isCanceled;

    /**
     * 设置取消发送标志
     * @return
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * 设置取消发送标记
     */
    public void cancel() {
        isCanceled = true;
    }
}
