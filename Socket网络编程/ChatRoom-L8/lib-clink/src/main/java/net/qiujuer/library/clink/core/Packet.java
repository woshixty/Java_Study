package net.qiujuer.library.clink.core;

/**
 * 公共数据的封装
 * 提供了类型以及基本的长度的定义
 */
public class Packet {
    protected byte type;
    protected int length;

    public byte type() {
        return type;
    }

    public int length() {
        return length;
    }
}
