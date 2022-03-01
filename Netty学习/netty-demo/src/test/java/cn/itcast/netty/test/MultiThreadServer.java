package cn.itcast.netty.test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class MultiThreadServer {
    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("Boss");
        final ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        final Selector boss = Selector.open();
        final SelectionKey bosskey = ssc.register(boss, 0, null);
        bosskey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));

        //1 创建固定数量的worker并初始化
        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }
//        final Worker worker = new Worker("workerSelector-0");
        final AtomicInteger index = new AtomicInteger();
        while (true) {
            boss.select();
            final Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                final SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    final SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}", sc.getRemoteAddress());
                    //2 关联selector
                    log.debug("before connected...{}", sc.getRemoteAddress());
                    //round robin 轮询
                    //boss 调用 初始化 selector，启动worker-0
                    workers[index.getAndIncrement() % workers.length].register(sc);
//                    worker.register(sc);  //初始化selector，启动worker-0
                    log.debug("after connected...{}", sc.getRemoteAddress());
                }
            }
        }
    }

    static class Worker implements Runnable {
        private Thread thread;
        private Selector workerSelector;
        private String name;
        private boolean start = false;
        private ConcurrentLinkedDeque<Runnable> queue = new ConcurrentLinkedDeque<>();

        public Worker(String name) {
            this.name = name;
        }

        //初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                thread = new Thread(this, name);
                thread.start();
                workerSelector = Selector.open();
                start = true;
            }
            //向队列里添加了任务，但是这个任务并没有立刻执行
            queue.add(() -> {
                try {
                    sc.register(workerSelector, SelectionKey.OP_READ, null);  //boss
                } catch (ClosedChannelException e) {
                    e.printStackTrace();
                }
            });
            workerSelector.wakeup();  //唤醒 workerSelector.select();
        }

        @SneakyThrows
        @Override
        public void run() {
            while (true) {
                workerSelector.select();  //worker-0
                final Runnable task = queue.poll();
                if (task != null) {
                    task.run();  //执行了sc.register
                }
                final Iterator<SelectionKey> iterator = workerSelector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    final SelectionKey key = iterator.next();
                    iterator.remove();
                    if (key.isReadable()) {
                        ByteBuffer buffer = ByteBuffer.allocate(16);
                        final SocketChannel channel = (SocketChannel) key.channel();
                        log.debug("read...{}", channel.getRemoteAddress());
                        channel.read(buffer);
                        buffer.flip();
                        debugAll(buffer);
                    }
                }
            }
        }
    }
}
