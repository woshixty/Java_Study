package net.qiujuer.library.clink.core;

import java.io.InputStream;

/**
 * 发送包的定义
 */
public abstract class SendPacket<T extends InputStream> extends Packet<T> {
    private boolean isCanceled;

    /**
     * 是否已取消发送
     * @return
     */
    public boolean isCanceled() {
        return isCanceled;
    }
}
