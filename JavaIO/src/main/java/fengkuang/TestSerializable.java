package fengkuang;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
public class TestSerializable {
    public static void main(String[] args) throws IOException {
        //创建一个ObjectOutputStream输出流
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object2.txt"));
        //将一个Person对象输出到输出流中
        Person per = new Person("xty", 18);
        oos.writeObject(per);
    }
}
