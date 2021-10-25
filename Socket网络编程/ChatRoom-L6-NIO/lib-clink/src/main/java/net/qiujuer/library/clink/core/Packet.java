package net.qiujuer.library.clink.core;

import java.io.Closeable;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 * 公共的数据封装
 * 提供了类型以及基本的长度的定义
 **/
public abstract class Packet implements Closeable {
    protected byte type;
    protected int length;

    public byte type() {
        return type;
    }

    public int length() {
        return length;
    }
}
