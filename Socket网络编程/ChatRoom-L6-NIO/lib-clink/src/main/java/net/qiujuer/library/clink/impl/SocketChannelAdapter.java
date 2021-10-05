package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.Receiver;
import net.qiujuer.library.clink.core.Sender;

import java.io.IOException;

public class SocketChannelAdapter implements Sender, Receiver, Cloneable {
    @Override
    public void setReceiveListener(IoArgs.IoArgsEventProcessor processor) {

    }

    @Override
    public boolean postReceiveAsync() throws IOException {
        return false;
    }

    @Override
    public void setSendListener(IoArgs.IoArgsEventProcessor processor) {

    }

    @Override
    public boolean postSendAsync() throws IOException {
        return false;
    }

    @Override
    public void close() throws IOException {

    }
}
