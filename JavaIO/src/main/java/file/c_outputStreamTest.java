package file;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/1/13
 **/
public class c_outputStreamTest {

    public static void main(String[] args) throws FileNotFoundException {
        /**
         * 和InputStream类似，OutputStream也是抽象类，它是所有输出流的超类
         * 这个抽象类定义的一个最重要的方法就是void write(int b) 如下：
         * public abstract void write(int b) throws IOException;
         */

        // 一个一个字节写
        try(OutputStream output = new FileOutputStream("src\\main\\resources\\hello.txt")) {
            output.write(72); // H
            output.write(101); // e
            output.write(108); // l
            output.write(108); // l
            output.write(111); // o
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 一次性写入若干字节
        try(OutputStream output = new FileOutputStream("src\\main\\resources\\hello.txt")) {
            output.write("Hello Java".getBytes("UTF-8"));
            output.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        /**
         * 和InputStream一样，OutputStream的write()方法也是阻塞的
         */
    }
}
