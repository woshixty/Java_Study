package net.qiujuer.library.clink.impl.async;

import net.qiujuer.library.clink.core.IoArgs;
import net.qiujuer.library.clink.core.SendDispatcher;
import net.qiujuer.library.clink.core.SendPacket;
import net.qiujuer.library.clink.core.Sender;
import net.qiujuer.library.clink.utils.CloseUtils;

import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicBoolean;

public class AsyncSendDispatcher implements SendDispatcher {
    //发送者
    private final Sender sender;
    //发送消息的队列
    private final Queue<SendPacket> queue = new ConcurrentLinkedDeque<>();
    //发送状态的维持
    private final AtomicBoolean isSending = new AtomicBoolean();
    //将发送的数据转换成IoArgs
    private IoArgs ioArgs = new IoArgs();
    //当前要发送的数据
    private SendPacket packetTemp;
    //当前packet的最大值
    private int total;
    //当前packet发送的进度
    private int position;
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    public AsyncSendDispatcher(Sender sender) {
        this.sender = sender;
    }

    @Override
    public void send(SendPacket packet) {
        queue.offer(packet);
        if (isSending.compareAndSet(false, true)) {

        }
    }

    @Override
    public void cancel(SendPacket packet) {

    }

    private SendPacket takePacket() {
        SendPacket packet = queue.poll();
        if (packet != null && packet.isCanceled()) {
            //已取消，不用发送
            return takePacket();
        }
        return packet;
    }

    private void sendNextPacket() {
        SendPacket temp = packetTemp;
        if (temp != null) {
            CloseUtils.close(temp);
        }

        SendPacket packet = packetTemp = takePacket();
        if (packet == null) {
            //队列为空，取消状态发送
            isSending.set(false);
            return;
        }
        total = packet.length();
        position = 0;
        sendCurrentPacket();
    }

    //真实发送当前packet
    private void sendCurrentPacket() {
        IoArgs args = ioArgs;
        args.startWriting();
        if (position >= total) {
            sendNextPacket();
            return;
        } else if(position == 0) {
            //首包，需要携带长度信息
            args.writeLength(total);
        }
        byte[] bytes = packetTemp.bytes();
        //将bytes的数据写入到IoArgs
        int count = args.readFrom(bytes, position);
        position += count;
        //完成封装
        args.finishWriting();

        try {
            sender.sendAsync(args, ioArgsEventListener);
        } catch (IOException e) {
            closeAndNotify();
            e.printStackTrace();
        }
    }

    private void closeAndNotify() {
        CloseUtils.close(this);
    }

    private final IoArgs.IoArgsEventListener ioArgsEventListener = new
            IoArgs.IoArgsEventListener() {
                @Override
                public void onStarted(IoArgs args) {

                }

                @Override
                public void onCompleted(IoArgs args) {
                    //继续发送当前包
                    sendCurrentPacket();
                }
            };

    @Override
    public void close() throws IOException {
        if (isClosed.compareAndSet(false, true)) {
            isSending.set(false);
            SendPacket packet = this.packetTemp;
            if (packet != null) {
                packetTemp = null;
                CloseUtils.close(packet);
            }
        }
    }
}
