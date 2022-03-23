package net.qiujuer.library.clink.core;

import com.sun.corba.se.spi.copyobject.ReflectiveCopyException;

import java.io.Closeable;

/**
 * 接收数据的封装调读
 * 把一份或者多份IoArgs组合成一份Packet
 */
public interface ReceiveDispatcher extends Closeable {
    void start();

    void stop();

    interface ReceivePacketCallback {
        /**
         * 当新到达一个Packet时
         * @param type
         * @param length
         * @return
         */
        ReceivePacket<?, ?> onArrivedNewPacket(byte type, long length);

        /**
         * 接收包完成时
         * @param packet
         */
        void onReceivePacketCompleted(ReceivePacket packet);

        /**
         * 收到心跳包
         */
        void onReceiveHeartbeat();
    }
}
