package cn.itcast.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;
import static cn.itcast.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class NewServer {

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整消息存入新的 ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                // 从 source 读，向 target 写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }

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
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        final int read = channel.read(buffer);
                        //如果是正常断开，read的值就是-1
                        if (read == -1) {
                            key.cancel();
                        } else {
//                            buffer.flip();
////                            debugRead(buffer);
//                            System.out.println(Charset.defaultCharset().decode(buffer));
                            split(buffer);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                        key.cancel();  //因为客户端断开了，因此需要将key取消（从selector的keys集合中真正删除key）
                    }
                }
//                key.cancel();
            }
        }
    }
}
