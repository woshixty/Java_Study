package net.qiujuer.lesson.sample.server.handle;

import net.qiujuer.lesson.sample.foo.Foo;
import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.core.Packet;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;
import java.util.concurrent.Executor;

/**
 * 客户端处理类
 */
public class ClientHandler extends Connector {
    private final File cachePath;
    private final String clientInfo;
    private final Executor deliveryPool;
    private final ConnectorCloseChain closeChain = new DefaultPrintConnectorCloseChain();
    private final ConnectorStringPacketChain stringPacketChain = new DefaultNonConnectorStringPacketChain();

    public ClientHandler(SocketChannel socketChannel, File cachePath, Executor deliveryPool) throws IOException {
        this.clientInfo = socketChannel.getRemoteAddress().toString();
        this.cachePath = cachePath;
        this.deliveryPool = deliveryPool;
        setup(socketChannel);
    }

    /**
     * 获取clientInfo
     * @return
     */
    public String getClientInfo() {
        return clientInfo;
    }

    /**
     * 客户端退出连接
     */
    public void exit() {
        CloseUtils.close(this);
        closeChain.handle(this, this);
    }

    /**
     * 客户端关闭时的回调
     * @param channel
     */
    @Override
    public void onChannelClosed(SocketChannel channel) {
        super.onChannelClosed(channel);
        closeChain.handle(this, this);
    }

    /**
     * 创建文件
     * @return
     */
    @Override
    protected File createNewReceiveFile() {
        return Foo.createRandomTemp(cachePath);
    }

    /**
     * 收到新的ReceivePacket
     * @param packet
     */
    @Override
    protected void onReceivedPacket(ReceivePacket packet) {
        super.onReceivedPacket(packet);
        switch (packet.type()) {
            case Packet.TYPE_MEMORY_STRING: {
                deliveryStringPacket((StringReceivePacket) packet);
                break;
            }
            default: {
                System.out.println("New Packet:" + packet.type() + "-" + packet.length());
            }
        }
    }

    /**
     * 责任链模式的调度
     * @param packet
     */
    private void deliveryStringPacket(StringReceivePacket packet) {
        deliveryPool.execute(() -> {stringPacketChain.handle(this, packet);});
    }

    public ConnectorStringPacketChain getStringPacketChain() {
        return stringPacketChain;
    }

    public ConnectorCloseChain getCloseChain() {
        return closeChain;
    }
}
