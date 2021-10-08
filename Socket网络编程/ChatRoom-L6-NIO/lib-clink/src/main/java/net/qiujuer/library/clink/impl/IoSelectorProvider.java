package net.qiujuer.library.clink.impl;

import net.qiujuer.library.clink.core.IoProvider;

import java.io.IOException;
import java.nio.channels.SocketChannel;

public class IoSelectorProvider implements IoProvider {
    @Override
    public boolean registerInput(SocketChannel channel, HandleInputCallback callback) {
        return false;
    }

    @Override
    public boolean registerOutput(SocketChannel channel, HandleOutputCallback callback) {
        return false;
    }

    @Override
    public void unRegisterInput(SocketChannel channel) {

    }

    @Override
    public void unRegisterOutput(SocketChannel channel) {

    }

    @Override
    public void close() throws IOException {

    }

    private void startRead() {
        Thread thread = new Thread("Clink IoSelector ReadSelector Thread") {
            @Override
            public void run() {
            }
        };
    }
}
