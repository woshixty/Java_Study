package net.qiujuer.lesson.sample.client;

import net.qiujuer.lesson.sample.client.bean.ServerInfo;
import net.qiujuer.lesson.sample.foo.Foo;
import net.qiujuer.lesson.sample.foo.handle.ConnectorHandler;
import net.qiujuer.lesson.sample.foo.handle.ConnectorStringPacketChain;
import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.core.Packet;
import net.qiujuer.library.clink.core.ReceivePacket;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.*;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;

/**
 * TCP连接代理类
 */
public class TCPClient extends ConnectorHandler {

    public TCPClient(SocketChannel socketChannel, File cachePath) throws IOException {
        super(socketChannel, cachePath);
        getStringPacketChain().appendLast(new PrintStringPacketChain());
    }

    private class PrintStringPacketChain extends ConnectorStringPacketChain {
        @Override
        protected boolean consume(ConnectorHandler handler, StringReceivePacket stringReceivePacket) {
            String str = stringReceivePacket.entity();
            System.out.println(str);
            return true;
        }
    }

    /**
     * 连接服务器并返回代理
     * @param info
     * @param cachePath
     * @return
     * @throws IOException
     */
    public static TCPClient startWith(ServerInfo info, File cachePath) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        // 连接本地，端口2000
        socketChannel.connect(new InetSocketAddress(Inet4Address.getByName(info.getAddress()), info.getPort()));
        // 打印信息
        System.out.println("已发起服务器连接, 并进入后续流程～");
        System.out.println("客户端信息:" + socketChannel.getLocalAddress());
        System.out.println("服务器信息:" + socketChannel.getRemoteAddress());
        try {
            return new TCPClient(socketChannel, cachePath);
        } catch (Exception e) {
            System.out.println("连接异常");
            CloseUtils.close(socketChannel);
        }
        return null;
    }
}
