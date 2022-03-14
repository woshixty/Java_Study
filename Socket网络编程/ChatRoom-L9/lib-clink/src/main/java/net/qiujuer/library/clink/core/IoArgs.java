package net.qiujuer.library.clink.core;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * IO输入和输出的参数类
 */
public class IoArgs {
    private int limit = 256;
    /**
    // 主要完成对ByteBuffer的封装
    private byte[] byteBuffer = new byte[256];
     */
    private ByteBuffer buffer = ByteBuffer.allocate(limit);

    /**
     * 将channel中的数据转移到buffer中
     * @param channel
     * @return
     */
    public int readFrom(ReadableByteChannel channel) throws IOException {
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
     * 写入数据到bytes中
     * @param channel
     * @return
     */
    public int writeTo(WritableByteChannel channel) throws IOException {
        int bytesProduced = 0;
        while (buffer.hasRemaining()) {
            int len = channel.write(buffer);
            if (len < 0)
                throw new EOFException();
            bytesProduced += len;
        }
        return bytesProduced;
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
        startWriting();
        buffer.putInt(total);
        finishWriting();
    }

    public int readLength() {
        return buffer.getInt();
    }

    public int capacity() {
        return buffer.capacity();
    }

    /**
     *  IoArgs提供者、处理者；数据的生产或消费者
     */
    public interface IoArgsEventProcessor {
        IoArgs provideIoArgs();

        /**
         * 消费失败
         * @param args
         */
        void onConsumeFailed(IoArgs args, Exception e);

        /**
         * 消费成功
         * @param args
         */
        void onConsumeCompleted(IoArgs args);
    }
}
