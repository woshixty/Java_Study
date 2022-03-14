package net.qiujuer.library.clink.core;

/**
 * 接收数据的封装调读
 * 把一份或者多份IoArgs组合成一份Packet
 */
public interface ReceiveDispatcher {
    void start();

    void stop();

    interface ReceivePacketCallback {
        void onReceivePacketCompleted(ReceivePacket packet);
    }
}
