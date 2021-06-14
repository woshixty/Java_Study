package fengkuang;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/15
 **/
public class RedirectIn {
    public static void main(String[] args) {
        try {
            FileInputStream fis = new FileInputStream("/Users/mr.stark/Desktop/test.java");
            //将标准输入重定向到fis输入流
            System.setIn(fis);
            //使用System.in创建Scanner对象，用于获取标准输入
            Scanner sc = new Scanner(System.in);
            //增加下面一行将回车作为分隔符
            sc.useDelimiter("\n");
            //判断是否有下一个输入项
            while (sc.hasNext()) {
                //输出输入项
                System.out.println(sc.next());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
