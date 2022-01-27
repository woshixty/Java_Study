package cn.itcast.netty.c1;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class TestByBuffer {
    public static void main(String[] args) {
        // FileChannel
        // 1.输入输出流、2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            // 准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            // 从channel读取数据，向buffer写入数据
            while (true) {
                int len = channel.read(buffer);
                if (len == -1)
                    break;
                // 打印buffer的内容
                buffer.flip();  // 切换至读模式
                while (buffer.hasRemaining()) {
                    // 检查是否还有剩余的未读数据
                    byte b = buffer.get();
                    System.out.println((char) b);
                }
                // 切换为写模式
                buffer.clear();  // 切换为写模式
            }
        } catch (IOException e) {
        }
    }
}
