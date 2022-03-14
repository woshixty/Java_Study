package net.qiujuer.library.clink.core;

public abstract class SendPacket extends Packet {
    private boolean isCanceled;

    /**
     * 发送的内容
     * @return
     */
    public abstract byte[] bytes();

    /**
     * 是否已取消发送
     * @return
     */
    public boolean isCanceled() {
        return isCanceled;
    }
}
