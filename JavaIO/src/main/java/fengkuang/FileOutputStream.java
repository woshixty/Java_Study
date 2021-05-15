package fengkuang;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/13
 **/
public class FileOutputStream {
    public static void main(String[] args) {
        try {
            //创建字节输入流
            InputStream fis = new FileInputStream("/Users/mr.stark/Desktop/Hello.java");
            //创建字节输出流
            OutputStream fos = new java.io.FileOutputStream("/Users/mr.stark/Desktop/Hello2.java");
            byte[] bbuf = new byte[32];
            int hasRead = 0;
            //循环取数据
            while ((hasRead = fis.read(bbuf)) > 0) {
                //每读取一次，即写入文件流，读多少写多少
                fos.write(bbuf, 0, hasRead);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
