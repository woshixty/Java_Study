package net.qiujuer.library.clink.core;

import java.io.OutputStream;

/**
 * 接收包的定义
 */
public abstract class ReceivePacket<T extends OutputStream> extends Packet<T> {
    /**
     * 把这方法加到公共类里面
     * 导入数据
     * @return
    public abstract OutputStream open();
    */
}
