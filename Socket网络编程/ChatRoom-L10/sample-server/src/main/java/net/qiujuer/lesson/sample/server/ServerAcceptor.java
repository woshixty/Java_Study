package net.qiujuer.lesson.sample.server;

import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;

/**
 * 客户端监听接受线程
 * 等待当前有哪些新的客户端需要连接
 */
public class ServerAcceptor extends Thread {
    private final AcceptListener listener;
    private final Selector selector;
    private final CountDownLatch latch = new CountDownLatch(1);
    private boolean done = false;

    public ServerAcceptor(AcceptListener listener) throws IOException {
        super("Server-Accept-Thread");
        this.listener = listener;
        this.selector = Selector.open();
    }

    /**
     * 等待该线程启动
     * @return
     */
    public boolean awaitRunning() {
        try {
            latch.await();
            return true;
        } catch (InterruptedException e) {
            return false;
        }
    }

    @Override
    public void run() {
        super.run();
        latch.countDown();
        Selector selector = this.selector;
        // 等待客户端连接
        do {
            // 得到客户端
            try {
                // 阻塞
                if (selector.select() == 0) {
                    if (done)
                        break;
                    continue;
                }
                // 当前有哪些事件就绪了
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    if (done)
                        break;
                    SelectionKey key = iterator.next();
                    iterator.remove();
                    // 检查当前Key的状态是否是我们关注的
                    // 客户端到达状态
                    if (key.isAcceptable()) {
                        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                        // 非阻塞状态拿到客户端连接
                        SocketChannel socketChannel = serverSocketChannel.accept();
                        listener.onNewSocketArrived(socketChannel);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } while (!done);
        System.out.println("ServerAccept Finished!");
    }

    void exit() {
        done = true;
        // 直接关闭Selector
        CloseUtils.close(selector);
    }

    interface AcceptListener {
        void onNewSocketArrived(SocketChannel channel);
    }
}
