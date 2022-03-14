package net.qiujuer.lesson.sample.server.handle;

import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.nio.channels.SocketChannel;

/**
 * 客户端处理
 */
public class ClientHandler extends Connector {
    private final ClientHandlerCallback clientHandlerCallback;
    private final String clientInfo;

    public ClientHandler(SocketChannel socketChannel, ClientHandlerCallback clientHandlerCallback) throws IOException {
        this.clientHandlerCallback = clientHandlerCallback;
        this.clientInfo = socketChannel.getRemoteAddress().toString();
        System.out.println("新客户端连接：" + clientInfo);
        setup(socketChannel);
    }

    public void exit() {
        CloseUtils.close(this);
        System.out.println("客户端已退出：" + clientInfo);
    }

    /**
     * 客户端关闭时的回调
     * @param channel
     */
    @Override
    public void onChannelClosed(SocketChannel channel) {
        super.onChannelClosed(channel);
        exitBySelf();
    }

    private void exitBySelf() {
        exit();
        clientHandlerCallback.onSelfClosed(this);
    }

    /**
     * 收到新消息时的回调
     * @param str
     */
    @Override
    protected void onReceiveNewMessage(String str) {
        super.onReceiveNewMessage(str);
        clientHandlerCallback.onNewMessageArrived(this, str);
    }

    /**
     * 客户端处理回调接口
     */
    public interface ClientHandlerCallback {
        // 自身关闭通知
        void onSelfClosed(ClientHandler handler);

        // 收到消息时通知
        void onNewMessageArrived(ClientHandler handler, String msg);
    }
}
