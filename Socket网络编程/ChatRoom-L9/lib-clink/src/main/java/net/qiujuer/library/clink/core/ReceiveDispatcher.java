package net.qiujuer.library.clink.core;

import java.io.Closeable;

/**
 * 接收数据的封装调读
 * 把一份或者多份IoArgs组合成一份Packet
 */
public interface ReceiveDispatcher extends Closeable {
    void start();

    void stop();

    interface ReceivePacketCallback {
        void onReceivePacketCompleted(ReceivePacket packet);
    }
}
