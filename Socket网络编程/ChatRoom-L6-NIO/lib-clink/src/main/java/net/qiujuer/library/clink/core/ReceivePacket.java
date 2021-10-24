package net.qiujuer.library.clink.core;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 * 接收包
 **/

public abstract class ReceivePacket extends Packet {
    //将数据存下来
    public abstract void save(byte[] bytes, int count);
}
