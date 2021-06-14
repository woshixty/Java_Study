package fengkuang;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
public class ReadFromProcess implements Serializable {
    public static void main(String[] args) throws IOException {
        //运行javac命令，返回运行该命令的子进程
        Process p = Runtime.getRuntime().exec("javac");
        //以p进程的错误流创建BufferedReader对象，这个错误流对本程序是输入流，对启动的进程是输出流
        BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream()));
        //循环读取p进程的错误输出
        String buff;
        while ( (buff = br.readLine()) != null ) {
            System.out.println(buff);
        }
    }
}
