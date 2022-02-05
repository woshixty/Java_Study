package cn.itcast.netty.c1;

import java.nio.ByteBuffer;

public class TestByteBufferAllocate {
    public static void main(String[] args) {
        System.out.println(ByteBuffer.allocate(16).getClass());
        System.out.println(ByteBuffer.allocateDirect(16).getClass());
        /**
         * class java.nio.HeapByteBuffer
         * 使用的Java堆内存：读写效率较低，收到GC的影响
         *
         * class java.nio.DirectByteBuffer
         * 直接内存：读写效率比较高（少一次数据的拷贝），不会受到GC的影响
         */
    }
}
