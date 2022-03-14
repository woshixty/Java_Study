package net.qiujuer.library.clink.core;

import java.io.IOException;
import java.io.InputStream;

/**
 * 发送包的定义
 */
public abstract class SendPacket<T extends InputStream> extends Packet {
    private boolean isCanceled;

    /**
     * 是否已取消发送
     * @return
     */
    public boolean isCanceled() {
        return isCanceled;
    }

    /**
     * 把这个方法加到公共类里面
     * 以流的形式发送数据，打开一个stream
     * @return
    public abstract InputStream open();
     */
}
