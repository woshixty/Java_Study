package net.qiujuer.library.clink.core;

import java.io.EOFException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

/**
 * IO输入和输出的参数类
 */
public class IoArgs {
    private int limit = 256;
    // 主要完成对ByteBuffer的封装
    private byte[] byteBuffer = new byte[256];
    private ByteBuffer buffer = ByteBuffer.wrap(byteBuffer);

    /**
     * 从bytes中读数据
     * @param bytes
     * @param offset
     * @return
     */
    public int readFrom(byte[] bytes, int offset) {
        int size = Math.min(bytes.length - offset, buffer.remaining());
        // 本次操作了多少个size
        buffer.put(bytes, offset, size);
        return size;
    }

    /**
     * 写入数据到bytes中
     * @param bytes
     * @param offset
     * @return
     */
    public int writeTo(byte[] bytes, int offset) {
        int size = Math.min(bytes.length - offset, buffer.remaining());
        // 本次操作了多少个size
        buffer.get(bytes, offset, size);
        return size;
    }

    /**
     * 从SocketChannel将数据读到buffer
     * @param channel
     * @return
     * @throws IOException
     */
    public int readFrom(SocketChannel channel) throws IOException {
        startWriting();
        int bytesProduced = 0;
        while (buffer.hasRemaining()) {
            int len = channel.read(buffer);
            if (len < 0)
                throw new EOFException();
            bytesProduced += len;
        }
        finishWriting();
        return bytesProduced;
    }

    /**
     * 开始将数据写入到IoArgs
     */
    public void startWriting() {
        buffer.clear();
        // 定义容纳区间
        buffer.limit(limit);
    }

    /**
     * 结束将数据写入到IoArgs之后调用
     */
    public void finishWriting() {
        buffer.flip();
    }

    /**
     * 设置单次写操作的容纳区间
     * @param limit
     */
    public void limit(int limit) {
        this.limit = limit;
    }

    /**
     * 将数据从buffer写到SocketChannel
     * @param channel
     * @return
     * @throws IOException
     */
    public int writeTo(SocketChannel channel) throws IOException {
        int bytesProduced = 0;
        while (buffer.hasRemaining()) {
            int len = channel.write(buffer);
            if (len < 0)
                throw new EOFException();
            bytesProduced += len;
        }
        return bytesProduced;
    }

    public void writeLength(int total) {
        buffer.putInt(total);
    }

    public int readLength() {
        return buffer.getInt();
    }

    public int capacity() {
        return buffer.capacity();
    }

    /**
     * buffer转换为String
     * @return
    public String bufferString() {
        // 丢弃换行符
        return new String(byteBuffer, 0, buffer.position() - 1);
    }
    */

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
