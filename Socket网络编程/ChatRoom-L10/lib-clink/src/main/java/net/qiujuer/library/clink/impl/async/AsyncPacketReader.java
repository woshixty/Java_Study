package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.Frame;
import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendPacket;
import net.qiujuer.library.clink.core.ds.BytePriorityNode;
import net.qiujuer.library.clink.frames.AbsSendPacketFrame;
import net.qiujuer.library.clink.frames.CancelSendFrame;
import net.qiujuer.library.clink.frames.SendEntityFrame;
import net.qiujuer.library.clink.frames.SendHeaderFrame;

import java.io.Closeable;
import java.io.IOException;

/**
 * 用于管理具体的packet发送与接收
 */
public class AsyncPacketReader implements Closeable {
    private final PacketProvider provider;
    private volatile IoArgs args = new IoArgs();
    // 存储所有帧的队列
    private volatile BytePriorityNode<Frame> node;
    // 表示当前队列长度
    private volatile int nodeSize = 0;
    // 每一个packet都有一个唯一标识
    private short lastIdentifier = 0;

    public AsyncPacketReader(PacketProvider provider) {
        this.provider = provider;
    }

    /**
     * 请求从 {@link #provider}队列中拿一份Packet进行发送
     *
     * @return 如果当前Reader中有可以用于网络发送的数据，则返回True
     */
    public synchronized boolean requestTakePacket() {
        synchronized (this) {
            if (nodeSize >= 1) {
                return true;
            }
        }
        SendPacket packet = provider.takePacket();
        // 加到队列
        if (packet != null) {
            // 创建头帧
            short identifier = generateIdentifier();
            SendHeaderFrame frame = new SendHeaderFrame(identifier, packet);
            appendNewFrame(frame);
        }
        synchronized (this) {
            return nodeSize != 0;
        }
    }

    /**
     * 填充数据
     *
     * @return
     */
    public IoArgs fillData() {
        Frame currentFrame = getCurrentFrame();
        // 如果当前帧为空
        if (currentFrame == null) {
            return null;
        }
        // 开始尝试向里面填充数据
        try {
            if (currentFrame.handle(args)) {
                // 消费完全了
                // 尝试基于本帧构建后续帧
                Frame nextFrame = currentFrame.nextFrame();
                if (nextFrame != null) {
                    appendNewFrame(nextFrame);
                } else if (currentFrame instanceof SendEntityFrame) {
                    // 是实体帧且没有下一帧
                    // 通知完成
                    provider.completedPacket(((SendEntityFrame) currentFrame).getPacket(), true);
                }
                // 从链头弹出
                popCurrentFrame();
            }
            return args;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 取消Packet对应的帧发送，如果当前Packet已发送部分数据（就算只是头数据）
     * 也应该在当前帧队列中发送一份取消发送的标志{@link CancelSendFrame}
     *
     * @param packet 待取消的packet
     */
    public synchronized void cancel(SendPacket packet) {
        // 如果当前的队列完全是一个空的东西
        if (nodeSize == 0) {
            return;
        }
        for (BytePriorityNode<Frame> x = node, before = null; x != null; before = x, x = x.next) {
            Frame frame = x.item;
            if (frame instanceof AbsSendPacketFrame) {
                // 是一个发送Packet的帧
                AbsSendPacketFrame packetFrame = (AbsSendPacketFrame) frame;
                // 我们想要取消的packet
                if (packetFrame.getPacket() == packet) {
                    // 终止操作
                    boolean removable = packetFrame.abort();
                    if (removable) {
                        // 完美终止
                        // A B C
                        removeFrame(x, before);
                        if (packetFrame instanceof SendHeaderFrame) {
                            // 头帧，并且未被发送任何数据，直接取消后不需要添加取消发送帧
                            break;
                        }
                    }
                    // 放入到唯一标识，添加终止帧，通知接收方
                    CancelSendFrame cancelSendFrame = new CancelSendFrame(packetFrame.getBodyIdentifier());
                    appendNewFrame(cancelSendFrame);
                    // 意外返回，返回失败
                    provider.completedPacket(packet, false);
                    break;
                }
            }
        }
    }

    /**
     * 关闭当前Reader，关闭时应关闭所有的Frame对应的Packet
     */
    @Override
    public synchronized void close() {
        // 将所有的帧关闭掉
        while (node != null) {
            Frame frame = node.item;
            if (frame instanceof AbsSendPacketFrame) {
                SendPacket packet = ((AbsSendPacketFrame) frame).getPacket();
                provider.completedPacket(packet, false);
            }
        }
        nodeSize = 0;
        node = null;
    }

    /**
     * 添加新的帧
     *
     * @param frame
     */
    private synchronized void appendNewFrame(Frame frame) {
        // 构建新的链表
        BytePriorityNode<Frame> newNode = new BytePriorityNode<>(frame);
        if (node != null) {
            // 使用优先级别添加到链表
            node.appendWithPriority(newNode);
        } else {
            node = newNode;
        }
        nodeSize++;
    }

    /**
     * 获取当前链表头的帧
     *
     * @return
     */
    private synchronized Frame getCurrentFrame() {
        if (node == null)
            return null;
        return node.item;
    }

    /**
     * 弹出链表头的帧
     */
    private void popCurrentFrame() {
        node = node.next;
        nodeSize--;
        if (node == null) {
            // 自主请求拿一个Packet
            requestTakePacket();
        }
    }

    /**
     * 删除某帧对应的链表节点
     *
     * @param removeNode 待删除的节点
     * @param before     当前删除节点的前一个节点，用于构建新的链表结构
     */
    private synchronized void removeFrame(BytePriorityNode<Frame> removeNode, BytePriorityNode<Frame> before) {
        if (before == null) {
            node = removeNode.next;
        } else {
            before.next = removeNode.next;
        }
        nodeSize--;
        if (node == null) {
            requestTakePacket();
        }
    }

    /**
     * 生成唯一标识
     *
     * @return
     */
    private short generateIdentifier() {
        short identifier = ++lastIdentifier;
        if (identifier == 255) {
            lastIdentifier = 0;
        }
        return identifier;
    }

    /**
     * Packet提供者
     */
    interface PacketProvider {
        /**
         * 拿Packet操作
         *
         * @return 如果队列有可以发送的Packet则返回不为null
         */
        SendPacket takePacket();

        /**
         * 结束一份Packet
         *
         * @param packet    发送包
         * @param isSucceed 是否成功发送完成
         */
        void completedPacket(SendPacket packet, boolean isSucceed);
    }
}
