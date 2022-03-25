package net.qiujuer.lesson.sample.foo.handle;

import net.qiujuer.lesson.sample.foo.Foo;
import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.core.IoContext;
import net.qiujuer.library.clink.core.Packet;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.File;
import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * 客户端处理类
 */
public class ConnectorHandler extends Connector {
    private final File cachePath;
    private final String clientInfo;
    private final ConnectorCloseChain closeChain = new DefaultPrintConnectorCloseChain();
    private final ConnectorStringPacketChain stringPacketChain = new DefaultNonConnectorStringPacketChain();

    public ConnectorHandler(SocketChannel socketChannel, File cachePath) throws IOException {
        this.clientInfo = socketChannel.getRemoteAddress().toString();
        this.cachePath = cachePath;
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
     * 外部调用的退出操作
     */
    public void exit() {
        CloseUtils.close(this);
    }

    /**
     * 内部监测到连接断开时回调
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
     * 避免阻塞当前的数据读取线程调度，则单独交给另外一个调度线程进行数据调度
     * @param packet
     */
    private void deliveryStringPacket(StringReceivePacket packet) {
        IoContext.get().getScheduler().delivery(() -> stringPacketChain.handle(this, packet));
    }

    /**
     * 获取当前链接的消息处理责任链链头
     * @return
     */
    public ConnectorStringPacketChain getStringPacketChain() {
        return stringPacketChain;
    }

    /**
     * 获取当前链接的关闭链接处理责任链链头
     * @return
     */
    public ConnectorCloseChain getCloseChain() {
        return closeChain;
    }
}
