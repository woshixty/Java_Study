package fengkuang;

import java.io.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
public class ReadObject {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        //创建一个ObjectInputStream输入流
        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("object.txt"));
        //读取Java对象并强转为Person对象
        Person p = (Person) ois.readObject();
        //输出该对象
        System.out.println(p);
    }
}
