package fengkuang;

import fengkuang.FileInputStreamTest;

import java.io.*;
import java.io.FileOutputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/13
 **/
public class PrintStreamTest {
    public static void main(String[] args) throws FileNotFoundException {
        OutputStream fos = new FileOutputStream("test.txt");
        PrintStream ps = new PrintStream(fos);
        //使用PrintStream执行输出
        ps.println("普通字符串");
        //直接使用PrintStream输出对象
        ps.println(new FileInputStreamTest());
    }
}
