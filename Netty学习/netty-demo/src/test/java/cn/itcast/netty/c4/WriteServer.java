package cn.itcast.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;

public class WriteServer {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        final Selector selector = Selector.open();
        ssc.register(selector, SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));

        while (true) {
            selector.select();
            final Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while (iter.hasNext()) {
                final SelectionKey key = iter.next();
                iter.remove();

                if (key.isAcceptable()) {
                    final SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector, 0, null);

                    //1 向客户端发送大量数据
                    final StringBuilder sb = new StringBuilder();
                    for (int i = 0; i < 3000000; i++) {
                        sb.append("a");
                    }
                    final ByteBuffer buffer = Charset.defaultCharset().encode(sb.toString());

                    //2 返回实际写入的字节数
                    final int write = sc.write(buffer);
                    System.out.println(write);

                    //3 判断是否有剩余内容
                    while (buffer.hasRemaining()) {
                        //4 关注可写事件
                        sckey.interestOps(sckey.interestOps() + SelectionKey.OP_WRITE);
                        //5 未写完的数据挂到sckey上面
                        sckey.attach(buffer);
                    }
                } else if (key.isWritable()) {
                    final ByteBuffer buffer = (ByteBuffer) key.attachment();
                    SocketChannel sc = (SocketChannel) key.channel();

                    final int write = sc.write(buffer);
                    System.out.println(write);

                    //6 清理工作
                    if (!buffer.hasRemaining()) {
                        key.attach(null);
                        //不需关注可写事件
                        key.interestOps(key.interestOps() - SelectionKey.OP_WRITE);
                    }
                }
            }
        }
    }
}
