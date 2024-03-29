package net.qiujuer.lesson.sample.server;

import net.qiujuer.lesson.sample.foo.Foo;
import net.qiujuer.lesson.sample.foo.handle.ConnectorHandler;
import net.qiujuer.lesson.sample.foo.handle.ConnectorCloseChain;
import net.qiujuer.lesson.sample.foo.handle.ConnectorStringPacketChain;
import net.qiujuer.library.clink.box.StringReceivePacket;
import net.qiujuer.library.clink.core.Connector;
import net.qiujuer.library.clink.core.schedule.IdleTimeoutScheduleJob;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TCPServer implements ServerAcceptor.AcceptListener, Group.GroupMessageAdapter {
    // 绑定本地的端口
    private final int port;
    // 文件目录
    private final File cachePath;
    // 交给每一个客户端处理器去处理
    private final List<ConnectorHandler> connectorHandlerList = new ArrayList<>();
    private final Map<String, Group> groups = new HashMap<>();
    private ServerAcceptor acceptor;
    private ServerSocketChannel server;

    private final ServerStatistics statistics = new ServerStatistics();

    public TCPServer(int port, File cachePath) {
        // TCP监听端口
        this.port = port;
        // 缓存文件
        this.cachePath = cachePath;
        this.groups.put(Foo.DEFAULT_GROUP_NAME, new Group(Foo.DEFAULT_GROUP_NAME, this));
    }

    /**
     * 开启一个TCP服务器
     * @return
     */
    public boolean start() {
        try {
            ServerAcceptor acceptor = new ServerAcceptor(this);

            // 打开一个通道套接字
            ServerSocketChannel server = ServerSocketChannel.open();
            // 设置为非阻塞
            server.configureBlocking(false);
            // 绑定本地端口
            server.socket().bind(new InetSocketAddress(port));
            // 注册客户端连接到达监听
            server.register(acceptor.getSelector(), SelectionKey.OP_ACCEPT);

            this.server = server;
            this.acceptor = acceptor;

            // 线程需要启动
            acceptor.start();
            if (acceptor.awaitRunning()) {
                System.out.println("服务器准备就绪～");
                // 打印本地服务器信息
                System.out.println("服务器信息：" + server.getLocalAddress().toString());
                return true;
            } else {
                System.out.println("启动异常");
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 关闭TCP服务器
     */
    public void stop() {
        if (acceptor != null)
            acceptor.exit();
        // 依次将客户端列表关闭
        ConnectorHandler[] connectorHandlers;
        synchronized (connectorHandlerList) {
            connectorHandlers = connectorHandlerList.toArray(new ConnectorHandler[0]);
            connectorHandlerList.clear();
        }
        for (ConnectorHandler connectorHandler : connectorHandlers)
            connectorHandler.exit();
        // 关闭channel和选择器
        CloseUtils.close(server);
    }

    /**
     * 广播消息
     * @param str
     */
    public void broadcast(String str) {
        str = "系统通知:" + str;
        ConnectorHandler[] connectorHandlers;
        synchronized (connectorHandlerList) {
            connectorHandlers = connectorHandlerList.toArray(new ConnectorHandler[0]);
        }
        for (ConnectorHandler connectorHandler : connectorHandlers)
            senMessageToClient(connectorHandler, str);
    }

    /**
     * 向客户端发送发一条消息
     * @param handler
     * @param msg
     */
    @Override
    public void senMessageToClient(ConnectorHandler handler, String msg) {
        handler.send(msg);
        statistics.sendSize++;
    }

    /**
     * 获取当前的状态信息
     * @return
     */
    Object[] getStatusString() {
        return new String[]{
                "客户端数量:" + connectorHandlerList.size(),
                "发送数量:" + statistics.sendSize,
                "接收数量:" + statistics.receiveSize
        };
    }

    /**
     * 新连接到达时回调
     * @param channel
     */
    @Override
    public void onNewSocketArrived(SocketChannel channel) {
        try {
            ConnectorHandler connectorHandler = new ConnectorHandler(channel, cachePath);
            System.out.println(connectorHandler.getClientInfo() + ":Connected!");
            // 添加收到消息时候的处理责任链
            connectorHandler.getStringPacketChain().appendLast(statistics.statisticsChain()).appendLast(new ParseCommandConnectorStringPacketChain());
            // 添加关闭链接时候的责任链
            connectorHandler.getCloseChain().appendLast(new RemoveQueueOnConnectorClosedChain());
            // 添加空闲任务调度
            IdleTimeoutScheduleJob scheduleJob = new IdleTimeoutScheduleJob(20, TimeUnit.SECONDS, connectorHandler);
            connectorHandler.schedule(scheduleJob);
            synchronized (connectorHandlerList) {
                connectorHandlerList.add(connectorHandler);
                System.out.println("当前客户端数量:" + connectorHandlerList.size());
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端连接异常:" + e.getMessage());
        }
    }

    /**
     * 移除操作
     */
    private class RemoveQueueOnConnectorClosedChain extends ConnectorCloseChain {
        @Override
        protected boolean consume(ConnectorHandler handler, Connector connector) {
            synchronized (connectorHandlerList) {
                connectorHandlerList.remove(handler);
                // 移除群聊的客户端
                Group group = groups.get(handler);
                if (group != null)
                    group.removeMember(handler);
            }
            return true;
        }
    }

    private class ParseCommandConnectorStringPacketChain extends ConnectorStringPacketChain {
        @Override
        protected boolean consume(ConnectorHandler handler, StringReceivePacket stringReceivePacket) {
            // 拿到当前的字符串
            String str = stringReceivePacket.entity();
            if (str.startsWith(Foo.COMMAND_GROUP_JOIN)) {
                Group group = groups.get(Foo.DEFAULT_GROUP_NAME);
                if (group.addMember(handler)) {
                    senMessageToClient(handler, "Join Group:" + group.getName());
                }
                return true;
            } else if (str.startsWith(Foo.COMMAND_GROUP_LEAVE)) {
                Group group = groups.get(Foo.DEFAULT_GROUP_NAME);
                if (group.removeMember(handler)) {
                    senMessageToClient(handler, "Leave Group:" + group.getName());
                }
                return true;
            }
            return false;
        }

        @Override
        protected boolean consumeAgain(ConnectorHandler handler, StringReceivePacket stringReceivePacket) {
            // 捡漏的模式，当我们第一遍未消费，然后又加入到群，自然没有后续节点消费
            // 此时我们进行二次消费，返回发送过来的消息
            senMessageToClient(handler, stringReceivePacket.entity());
            return true;
        }
    }
}
