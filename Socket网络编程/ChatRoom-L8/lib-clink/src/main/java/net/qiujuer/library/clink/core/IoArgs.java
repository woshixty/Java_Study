package net.qiujuer.library.clink.core;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * IO输入和输出的参数类
 */
public class IoArgs {
    // 主要完成对ByteBuffer的封装
    private byte[] byteBuffer = new byte[256];
    private ByteBuffer buffer = ByteBuffer.wrap(byteBuffer);

    /**
     * 从SocketChannel将数据读到buffer
     * @param channel
     * @return
     * @throws IOException
     */
    public int read(SocketChannel channel) throws IOException {
        buffer.clear();
        return channel.read(buffer);
    }

    /**
     * 将数据从buffer写到SocketChannel
     * @param channel
     * @return
     * @throws IOException
     */
    public int write(SocketChannel channel) throws IOException {
        return channel.write(buffer);
    }

    /**
     * buffer转换为String
     * @return
     */
    public String bufferString() {
        // 丢弃换行符
        return new String(byteBuffer, 0, buffer.position() - 1);
    }

    /**
     * 用来做监听的，监听IO Args的状态
     */
    public interface IoArgsEventListener {
        /**
         * 开始监听时回调
         * @param args
         */
        void onStarted(IoArgs args);

        /**
         * 完成时回调
         * @param args
         */
        void onCompleted(IoArgs args);
    }
}
