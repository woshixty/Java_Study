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
    private ByteBuffer buffer = ByteBuffer.allocate(256);

    /**
     * 从bytes数组进行消费
     * @param bytes
     * @param offset
     * @param count
     * @return
     */
    public int readFrom(byte[] bytes, int offset, int count) {
        int size = Math.min(count, buffer.remaining());
        if (size < 0)
            return 0;
        buffer.put(bytes, offset, size);
        return size;
    }

    /**
     * 写数据到bytes中去
     * @param bytes
     * @param offset
     * @return
     */
    public int writeTo(byte[] bytes, int offset) {
        int size = Math.min(bytes.length - offset, buffer.remaining());
        buffer.get(bytes, offset, size);
        return size;
    }

    /**
     * 将ReadableByteChannel中的数据转移到buffer中
     * @param channel
     * @return
     */
    public int readFrom(ReadableByteChannel channel) throws IOException {
        int bytesProduced = 0;
        while (buffer.hasRemaining()) {
            int len = channel.read(buffer);
            if (len < 0)
                throw new EOFException();
            bytesProduced += len;
        }
        return bytesProduced;
    }

    /**
     * 将WritableByteChannel数据写入到bytes中
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
        this.limit = Math.min(limit, buffer.capacity());
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

    public int readLength() {
        return buffer.getInt();
    }

    public int capacity() {
        return buffer.capacity();
    }

    /**
     * 返回当前buffer的可存取区间
     * @return
     */
    public boolean remained() {
        return buffer.remaining() > 0;
    }

    /**
     * 填充假数据
     * @param size
     * @return
     */
    public int fillEmpty(int size) {
        // 本次可以消费的最大区间
        int fillSize = Math.min(size, buffer.remaining());
        buffer.position(buffer.position() + fillSize);
        return 0;
    }

    /**
     *  IoArgs提供者、处理者；数据的生产或消费者
     */
    public interface IoArgsEventProcessor {
        /**
         * 提供一个IoArgs
         * @return
         */
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
