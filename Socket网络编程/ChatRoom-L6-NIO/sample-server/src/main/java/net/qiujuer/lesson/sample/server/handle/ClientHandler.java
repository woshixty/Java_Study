package net.qiujuer.lesson.sample.server.handle;

import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ClientHandler extends Connector {
//    private final Connector connector;
//    private final ClientWriteHandler writeHandler;
    private final ClientHandlerCallback clientHandlerCallback;
    private final String clientInfo;

    public ClientHandler(SocketChannel socketChannel, ClientHandlerCallback clientHandlerCallback) throws IOException {
        this.clientHandlerCallback = clientHandlerCallback;
        this.clientInfo = socketChannel.getRemoteAddress().toString();
        System.out.println("新客户端连接：" + clientInfo);
        setup(socketChannel);

//        connector = new Connector() {
//            @Override
//            public void onChannelClosed(SocketChannel channel) {
//                super.onChannelClosed(channel);
//                exitBySelf();
//            }
//
//            @Override
//            protected void onReceiveNewMessage(String str) {
//                super.onReceiveNewMessage(str);
//                clientHandlerCallback.onNewMessageArrived(ClientHandler.this, str);
//            }
//        };
//        connector.setup(socketChannel);
//        Selector writeSelector = Selector.open();
//        socketChannel.register(writeSelector, SelectionKey.OP_WRITE);
//        this.writeHandler = new ClientWriteHandler(writeSelector);
    }

    public void exit() {
        CloseUtils.close(this);
        System.out.println("客户端已退出：" + clientInfo);
    }

    @Override
    public void onChannelClosed(SocketChannel channel) {
        super.onChannelClosed(channel);
        exitBySelf();
    }

    private void exitBySelf() {
        exit();
        clientHandlerCallback.onSelfClosed(this);
    }

    @Override
    protected void onReceiveNewMessage(String str) {
        super.onReceiveNewMessage(str);
        clientHandlerCallback.onNewMessageArrived(this, str);
    }

    public interface ClientHandlerCallback {
        //自身关闭以后进行通知
        void onSelfClosed(ClientHandler handler);

        //接收到消息时通知
        void onNewMessageArrived(ClientHandler handler, String msg);
    }

//    class ClientWriteHandler {
//        private boolean done = false;
//        private final Selector selector;
//        private final ByteBuffer byteBuffer;
//        private final ExecutorService executorService;
//
//        ClientWriteHandler(Selector selector) {
//            this.selector = selector;
//            this.byteBuffer = ByteBuffer.allocate(256);
//            this.executorService = Executors.newSingleThreadExecutor();
//        }
//
//        void exit() {
//            done = true;
//            CloseUtils.close(selector);
//            executorService.shutdownNow();
//        }
//
//        void send(String str) {
//            if (done)
//                return;
//            executorService.execute(new WriteRunnable(str));
//        }
//
//        class WriteRunnable implements Runnable {
//            private final String msg;
//            WriteRunnable(String msg) {
//                this.msg = msg + '\n';
//            }
//            @Override
//            public void run() {
//                if (ClientWriteHandler.this.done) {
//                    return;
//                }
//                //清空数据
//                byteBuffer.clear();
//                //传入数据
//                byteBuffer.put(msg.getBytes());
//                //反转操作，重点
//                byteBuffer.flip();
//                while (!done && byteBuffer.hasRemaining()) {
//                    try {
//                        int len = socketChannel.write(byteBuffer);
//                        if (len < 0) {
//                            System.out.println("客户端已无法发送数据！");
//                            ClientHandler.this.exitBySelf();
//                            break;
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }
//    }
}
