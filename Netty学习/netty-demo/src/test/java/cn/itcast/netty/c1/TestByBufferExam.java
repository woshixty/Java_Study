package cn.itcast.netty.c1;

import java.nio.ByteBuffer;
import static cn.itcast.netty.c1.ByteBufferUtil.debugAll;

public class TestByBufferExam {
    /**
     * 消息粘包
     */
    public static void main(String[] args) {
        ByteBuffer source = ByteBuffer.allocate(32);
        source.put("Hello World\nI'm ZhangSan\nHo".getBytes());
        split(source);
        source.put("w are you?\n".getBytes());
        split(source);
    }

    private static void split(ByteBuffer source) {
        source.flip();
        //找到一条完整消息
        for (int i = 0; i < source.limit(); i++) {
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                //将消息存储到新的ByteBuffer中
                ByteBuffer target = ByteBuffer.allocate(length);
                //从source读，向target写入
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}
