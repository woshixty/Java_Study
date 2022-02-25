package cn.itcast.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static cn.itcast.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class Server {
    public static void main(String[] args) throws IOException {
        //使用nio来理解阻塞模式，单线程

        //0 ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        //1 创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);  //非阻塞模式

        //2 绑定监听端口
        ssc.bind(new InetSocketAddress(8080));

        //3 建立连接的集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            //4 accept建立与客户端连接，SocketChannel用来与客户端之间通信
            log.debug("connecting...");
            SocketChannel sc = ssc.accept();  //阻塞方法，线程停止，改了以后变成非阻塞模式，如果没有连接建立，sc是null
            if (sc != null) {
                log.debug("connected...");
                sc.configureBlocking(false);  //设置为非阻塞模式
                channels.add(sc);
            }
            for (SocketChannel channel : channels) {
                //5 接受客户端发过来的数据
                log.debug("before read... {}", channel);
                final int read = channel.read(buffer);//阻塞方法，线程停止，设置以后变为非阻塞，没有读到数据就返回0
                //切换至读模式
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read... {}", channel);
                }
            }
        }
    }
}
