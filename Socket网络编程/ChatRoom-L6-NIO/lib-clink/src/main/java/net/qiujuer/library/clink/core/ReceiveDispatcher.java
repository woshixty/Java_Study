package net.qiujuer.library.clink.core;

import java.io.Closeable;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/10/24
 * 接收的数据调度封装
 * 把一份或者多份IoArgs组合成一份Packet
 **/
public interface ReceiveDispatcher extends Closeable {
    void start();

    void stop();

    interface ReceivePacketCallback {
        void onReceivePacketCompleted(ReceivePacket packet);
    }
}
