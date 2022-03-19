package net.qiujuer.library.clink.frames;

import net.qiujuer.library.clink.core.Frame;
import net.qiujuer.library.clink.core.IoArgs;

import java.io.IOException;

/**
 * 基础的发送帧
 */
public abstract class AbsSendFrame extends Frame {
    // 帧头可读写区域大小
    // 保证多线程可见
    volatile byte headerRemaining = Frame.FRAME_HEADER_LENGTH;
    // 帧体可读写区域大小
    volatile int bodyRemaining;

    public AbsSendFrame(int length, byte type, byte flag, short identifier) {
        super(length, type, flag, identifier);
        bodyRemaining = length;
    }

    /**
     * 数据消费部分，同步方法
     * @param args 数据
     * @return
     * @throws IOException
     */
    @Override
    public synchronized boolean handle(IoArgs args) throws IOException {
        try {
            args.limit(headerRemaining + bodyRemaining);
            args.startWriting();
            // 消费头部数据
            if (headerRemaining > 0 && args.remained()) {
                headerRemaining -= consumeHeader(args);
            }
            // 消费body部分数据
            if (headerRemaining == 0 && args.remained() && bodyRemaining > 0) {
                bodyRemaining -= consumeBody(args);
            }
            // 完全消费完成
            return headerRemaining == 0 && bodyRemaining == 0;
        } finally {
            args.finishWriting();
        }
    }

    @Override
    public int getConsumableLength() {
        return headerRemaining + bodyRemaining;
    }

    /**
     * 消费头部数据
     * @param args
     * @return
     */
    private byte consumeHeader(IoArgs args) {
        int count = headerRemaining;
        // 总长度减去已消费的部分得到即将消费的部分
        int offset = header.length - count;
        return (byte) args.readFrom(header, offset, count);
    }

    protected abstract int consumeBody(IoArgs args) throws IOException;

    /**
     * 是否已经处于发送数据中，如果已经发送了部分数据则返回True
     * 只要头部数据已经开始消费，则肯定已经处于发送数据中
     *
     * @return True，已发送部分数据
     */
    protected synchronized boolean isSending() {
        return headerRemaining < Frame.FRAME_HEADER_LENGTH;
    }
}
