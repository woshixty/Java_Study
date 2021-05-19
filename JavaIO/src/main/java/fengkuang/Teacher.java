package fengkuang;

import lombok.Data;

import java.io.*;
import java.io.FileOutputStream;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/19
 **/
@Data
public class Teacher implements Serializable {
    private String name;
    private Person person;
    public Teacher(String name, Person person) {
        this.name = name;
        this.person = person;
    }

    public static void main(String[] args) throws IOException {
        Person person = new Person("孙悟空", 500);
        Teacher t1 = new Teacher("唐僧", person);
        Teacher t2 = new Teacher("菩提祖师", person);

        //创建一个ObjectOutputStream输出流
        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("object.txt"));
        //将一个Person对象输出到输出流中
        Person per = new Person("xty", 18);

        oos.writeObject(t1);
        oos.writeObject(t2);
        oos.writeObject(per);
    }
}
