package net.qiujuer.library.clink.frames;

import net.qiujuer.library.clink.core.Frame;
import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendPacket;

import java.io.IOException;
import java.nio.channels.ReadableByteChannel;

/**
 * 数据的一些帧
 */
public class SendEntityFrame extends AbsSendPacketFrame {
    // 用来做数据读取的
    private final ReadableByteChannel channel;
    // 未消费的长度，即未发送的数据的长度
    private final long unConsumeEntityLength;

    /**
     * 构造方法
     * @param identifier
     * @param entityLength  长度信息
     * @param channel
     * @param packet
     */
    public SendEntityFrame(short identifier,
                           long entityLength,
                           ReadableByteChannel channel,
                           SendPacket packet) {
        super((int) Math.min(entityLength, Frame.MAX_CAPACITY),
                Frame.TYPE_PACKET_ENTITY,
                Frame.FLAG_NONE,
                identifier,
                packet);
        // 1234567890
        // 1234 5678 90
        // 10 4,6 4,2 2
        // 总长度-当前我们可以消费的body部分的数据长度 = 未消费的数据长度
        this.unConsumeEntityLength = entityLength - bodyRemaining;
        this.channel = channel;
    }

    /**
     * 消费数据，即将数据填充到IoArgs
     * @param args
     * @return
     * @throws IOException
     */
    @Override
    protected int consumeBody(IoArgs args) throws IOException {
        if (packet == null) {
            // 已终止当前帧，则填充假数据
            return args.fillEmpty(bodyRemaining);
        }
        // 将ReadableByteChannel中的数据转移到buffer中
        return args.readFrom(channel);
    }

    /**
     * 构建下一帧
     * @return
     */
    @Override
    public Frame buildNextFrame() {
        // 如果所有数据已经消费完成
        if (unConsumeEntityLength == 0) {
            return null;
        }
        // 将未消费的长度用于构建下一帧
        return new SendEntityFrame(getBodyIdentifier(), unConsumeEntityLength, channel, packet);
    }
}
