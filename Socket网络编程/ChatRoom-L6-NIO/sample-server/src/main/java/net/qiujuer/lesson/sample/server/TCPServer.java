package net.qiujuer.lesson.sample.server;

import net.qiujuer.lesson.sample.server.handle.ClientHandler;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TCPServer implements ClientHandler.ClientHandlerCallback {
    private final int port;
    private ClientListener listener;
    private List<ClientHandler> clientHandlerList = new ArrayList<>();
    //单线程池
    private final ExecutorService forwardingThreadPoolExecutor;
    //将Selector保存为一个变量
    private Selector selector;
    private ServerSocketChannel server;

    public TCPServer(int port) {
        this.port = port;
        this.forwardingThreadPoolExecutor = Executors.newSingleThreadExecutor();
    }

    public boolean start() {
        try {
            //打开一个Selector
            selector = Selector.open();
            //启动一个ServerSocket通道
            ServerSocketChannel server = ServerSocketChannel.open();
            //配置为非阻塞
            server.configureBlocking(false);
            //綁定本地端口
            server.socket().bind(new InetSocketAddress(port));
            //注册客户端连接到达监听
            server.register(selector, SelectionKey.OP_ACCEPT);

            this.server = server;

            System.out.println("服务器信息：" + server.getLocalAddress().toString());

            //启动客户端的监听
            ClientListener listener = this.listener = new ClientListener();
            listener.start();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public void stop() {
        if (listener != null) {
            listener.exit();
        }

        CloseUtils.close(server);
        CloseUtils.close(selector);

        synchronized (TCPServer.this) {
            for (ClientHandler clientHandler : clientHandlerList) {
                clientHandler.exit();
            }
            clientHandlerList.clear();
        }
        //停止线程池
        forwardingThreadPoolExecutor.shutdownNow();
    }

    public synchronized void broadcast(String str) {
        for (ClientHandler clientHandler : clientHandlerList) {
            clientHandler.send(str);
        }
    }

    @Override
    public synchronized void onSelfClosed(ClientHandler handler) {
        clientHandlerList.remove(handler);
    }

    //新消息到来的时候
    @Override
    public void onNewMessageArrived(ClientHandler handler, String msg) {
        //异步提交转发任务
        forwardingThreadPoolExecutor.execute(() -> {
            synchronized (TCPServer.this) {
                for (ClientHandler clientHandler : clientHandlerList) {
                    if (clientHandler.equals(handler)) {
                        //跳过自己
                        continue;
                    }
                    //对其他客户端发送消息
                    clientHandler.send(msg);
                }
            }
        });
    }

    private class ClientListener extends Thread {
        private boolean done = false;

        @Override
        public void run() {
            super.run();
            //将成员变量转化为局部变量
            Selector selector = TCPServer.this.selector;
            System.out.println("服务器准备就绪～");
            //等待客户端连接
            do {
                //得到客户端
                Socket client;
                try {
                    if (selector.select() == 0) {
                        if (done) {
                            break;
                        }
                        continue;
                    }
                    //遍历
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while (iterator.hasNext()) {
                        if (done) {
                            break;
                        }
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        //检查当前的Key的状态是否是当前我们关注的状态
                        //客户端到达的状态
                        if (key.isAcceptable()) {
                            ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                            //非阻塞状态拿到客户端连接
                            SocketChannel socketChannel = serverSocketChannel.accept();

                            try {
                                //客户端构建异步线程
                                ClientHandler clientHandler = new ClientHandler(socketChannel, TCPServer.this);
                                //添加同步处理
                                synchronized (TCPServer.this) {
                                    clientHandlerList.add(clientHandler);
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                                System.out.println("客户端连接异常：" + e.getMessage());
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } while (!done);
            System.out.println("服务器已关闭！");
        }

        void exit() {
            done = true;
            //唤醒当前阻塞
            selector.wakeup();
        }
    }
}
