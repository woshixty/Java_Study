package net.qiujuer.library.clink.frames;

import net.qiujuer.library.clink.core.Frame;
import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendPacket;

import java.io.IOException;
import java.io.InputStream;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 * 发送的Packet头帧，第一个帧
 */
public class SendHeaderFrame extends AbsSendPacketFrame {
    // 包头帧最小长度
    static final int PACKET_HEADER_FRAME_MIN_LENGTH = 6;
    private final byte[] body;

    /**
     * 首帧构造方法
     * @param identifier  唯一标识
     * @param packet  发送包
     */
    public SendHeaderFrame(short identifier, SendPacket packet) {
        super(PACKET_HEADER_FRAME_MIN_LENGTH,
                Frame.TYPE_PACKET_HEADER,
                Frame.FLAG_NONE,
                identifier,
                packet);
        // 数据长度
        final long packetLength = packet.length();
        final byte packetType = packet.type();
        final byte[] packetHeaderInfo = packet.headerInfo();

        // 头部对应的数据信息长度，剩余可消费的数据长度
        body = new byte[bodyRemaining];

        // 头5字节存储长度信息低5字节（40位）数据
        // 00000000 00000000 00000000 00000000 00000000 00000000 00000000 00000000
        body[0] = (byte) (packetLength >> 32);
        body[1] = (byte) (packetLength >> 24);
        body[2] = (byte) (packetLength >> 16);
        body[3] = (byte) (packetLength >> 8);
        body[4] = (byte) (packetLength);

        body[5] = packetType;

        if (packetHeaderInfo != null) {
            // 将packet中的数据复制到body部分
            System.arraycopy(packetHeaderInfo, 0,
                    body, PACKET_HEADER_FRAME_MIN_LENGTH, packetHeaderInfo.length);
        }
    }

    /**
     * 消耗body部分
     * @param args
     * @return
     * @throws IOException
     */
    @Override
    protected int consumeBody(IoArgs args) {
        int count = bodyRemaining;
        int offset = body.length - count;
        // 将body里面的数据放入到args的buffer里面
        return args.readFrom(body, offset, count);
    }

    /**
     * 构建下一帧
     * @return
     */
    @Override
    public Frame buildNextFrame() {
        // 构建
        InputStream stream = packet.open();
        ReadableByteChannel channel = Channels.newChannel(stream);
        return new SendEntityFrame(getBodyIdentifier(), packet.length(), channel, packet);
    }
}
