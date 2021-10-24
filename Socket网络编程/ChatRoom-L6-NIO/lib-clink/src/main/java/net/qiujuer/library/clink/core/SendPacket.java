package net.qiujuer.library.clink.core;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 * 发送包
 **/
public abstract class SendPacket extends Packet {
    //是否已取消
    private boolean isCanceled;

    //发送的内容
    public abstract byte[] bytes();

    public boolean isCanceled() {
        return isCanceled;
    }
}
