package file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/30
 **/
public class b_inputStreamTest {
    public static void main(String[] args) throws IOException {
        /**
         * FileInputStream是InputStream的一个子类
         * 顾名思义，FileInputStream就是从文件流中读取数据
         * 下面的代码演示了如何完整地读取一个FileInputStream的所有字节
         */
        InputStream inputStream = new FileInputStream("src\\main\\resources\\test.txt");
        for ( ; ; ) {
            int n = inputStream.read();
            if (n==-1)
                break;
            System.out.println((char) n);
        }
        /**
         * InputStream和OutputStream都是通过close()方法来关闭流
         * 关闭流就会释放对应的底层资源
         */
        inputStream.close();

        /**
         * 仔细观察上面的代码，会发现一个潜在的问题：
         * 如果读取过程中发生了IO错误，InputStream就没法正确地关闭，资源也就没法及时释放
         * 因此，我们需要用try ... finally来保证InputStream在无论是否发生IO错误的时候都能够正确地关闭
         * 如下代码
         */
        try(InputStream inputStream1 = new FileInputStream("src\\main\\resources\\test.txt")) {
            int n;
            while ((n=inputStream1.read()) != -1)
                System.out.println(n);
        }  //编译器会在这里自动写入finally并写入close()

        /**
         * 缓冲
         * 在读取流的时候，一次读取一个字节并不是最高效的方法
         * 很多流支持一次性读取多个字节到缓冲区，对于文件和网络流来说，利用缓冲区一次性读取多个字节效率往往要高很多
         * InputStream提供了两个重载方法来支持读取多个字节
         *
         * int read(byte[] b)：  读取若干字节并填充到byte[]数组，返回读取的字节数
         * int read(byte[] b, int off, int len)：  指定byte[]数组的偏移量和最大填充数
         */
        try(InputStream inputStream1 = new FileInputStream("src\\main\\resources\\test.txt")) {
            byte[] bytes = new byte[1000];  //定义 1000 大小的缓冲区
            int n;
            while ((n=inputStream1.read(bytes)) != -1)  //读取到缓冲区
                System.out.println("read" + n + "bytes");
        }

        /**
         * 阻塞
         * 在调用InputStream的read()方法读取数据时，我们说read()方法是阻塞（Blocking）的
         * 比如像下面的代码
         */
        int n;
        n = inputStream.read();  //即只有当 read() 执行完以后才能执行下一段代码
        int m = n;
    }

    private static String readAsString(InputStream inputStream) throws IOException {
        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = inputStream.read()) != -1) {
            sb.append((char) n);
        }
        return sb.toString();
    }
}
