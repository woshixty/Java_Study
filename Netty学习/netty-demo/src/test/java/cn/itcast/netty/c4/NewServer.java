package cn.itcast.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static cn.itcast.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class NewServer {
    public static void main(String[] args) throws IOException {
        //1 创建Selector 管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2 建立selector 和 channel的联系（注册）
        //SelectionKey事件发生后通过它可以知道事件和那个channel的事件
        //accept、connect、read、write四种事件
        //accept-会在有连接请求时触发
        //connect-客户端连接建立后触发
        //read-可读事件
        //write-可写事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        //key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("1{}", sscKey);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            //3 select方法，没有事件发生-线程阻塞，有事件恢复运行
            //select在事件未处理的时候，不会阻塞
            selector.select();

            //4 处理事件 里面是所有的发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator(); // accept、read
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                //处理key时，要从selectedKeys集合中删除
                iter.remove();
                log.debug("2{}", key);

                //5 区分事件类型
                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    final SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);
                    log.debug("3{}", sc);
                } else if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(16);
                    channel.read(buffer);
                    buffer.flip();
                    debugRead(buffer);
                }
//                key.cancel();
            }
        }
    }
}
