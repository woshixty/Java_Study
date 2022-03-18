package net.qiujuer.library.clink.frames;

import net.qiujuer.library.clink.core.IoArgs;

import java.io.IOException;

/**
 * 取消传输帧，接收实现，数据全都在帧头里面
 */
public class CancelReceiveFrame extends AbsReceiveFrame {

    CancelReceiveFrame(byte[] header) {
        super(header);
    }

    @Override
    protected int consumeBody(IoArgs args) {
        return 0;
    }
}
