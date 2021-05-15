package fengkuang;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/13
 **/
public class FileInputStreamTest {

    public static void main(String[] args) throws IOException {
        //创建字节输入流
        InputStream fis = new FileInputStream("/Users/mr.stark/Desktop/Java_Study/JavaIO/src/main/java/file/JY_File.java");
        //创建一个长度为1024的容器来取东西
        byte[] bbuf = new byte[1024];
        //用于保存实际读取的字节数
        int hasRead = 0;
        //循环读取数据
        while ((hasRead = fis.read(bbuf)) > 0) {
            //将读取的字节数组转换成字符串输入
            System.out.println(new String(bbuf));
        }
    }
}
