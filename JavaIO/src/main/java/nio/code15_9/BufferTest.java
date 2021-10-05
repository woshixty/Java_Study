package nio.code15_9;

import java.nio.CharBuffer;

public class BufferTest {
    private static void printInfo(CharBuffer buffer) {
        System.out.println("capacity：" + buffer.capacity());
        System.out.println("limit：" + buffer.limit());
        System.out.println("position：" + buffer.position());
        System.out.println("---------------------------------");
        System.out.println();
    }

    public static void main(String[] args) {
        //创建Buffer
        CharBuffer buffer = CharBuffer.allocate(8);
        //打印Buffer当前信息
        printInfo(buffer);
        //放入元素
        buffer.put("a");
        buffer.put("b");
        buffer.put("c");
        System.out.println("放入三个元素之后Buffer信息：");
        printInfo(buffer);
        //调用flip()方法
        buffer.flip();
        System.out.println("执行flip()方法以后Buffer信息：");
        //取出第一个元素
        System.out.println("第一个元素：" + buffer.get());
        System.out.println("取出第一个元素以后Buffer信息：");
        printInfo(buffer);
        //调用clear()方法
        buffer.clear();
        System.out.println("调用clear()方法Buffer信息：");
        printInfo(buffer);
        //取出第二个元素
        System.out.println("第二个元素：" + buffer.get(2));
        System.out.println("取出第二个元素以后Buffer信息：");
        printInfo(buffer);
    }
}
