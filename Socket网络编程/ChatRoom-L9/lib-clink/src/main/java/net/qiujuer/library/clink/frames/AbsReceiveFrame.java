package net.qiujuer.library.clink.frames;

import net.qiujuer.library.clink.core.Frame;
import net.qiujuer.library.clink.core.IoArgs;

import java.io.IOException;

/**
 * 接收帧的实现
 * 根据帧头构建接收帧
 */
public abstract class AbsReceiveFrame extends Frame {
    // 帧体可读写区域大小
    volatile int bodyRemaining;

    AbsReceiveFrame(byte[] header) {
        super(header);
        // 拿到当前帧体长度
        bodyRemaining = getBodyLength();
    }

    /**
     * 接收数据
     * @param args 数据
     * @return
     * @throws IOException
     */
    @Override
    public synchronized boolean handle(IoArgs args) throws IOException {
        if (bodyRemaining == 0) {
            // 已读取所有数据
            return true;
        }
        // 消费body
        bodyRemaining -= consumeBody(args);
        return bodyRemaining == 0;
    }

    /**
     * 针对发送而言
     * @return
     */
    @Override
    public final Frame nextFrame() {
        return null;
    }

    @Override
    public int getConsumableLength() {
        return bodyRemaining;
    }

    protected abstract int consumeBody(IoArgs args) throws IOException;
}
