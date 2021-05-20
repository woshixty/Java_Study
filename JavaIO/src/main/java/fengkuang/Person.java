package fengkuang;

import lombok.Data;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
@Data
public class Person implements Serializable {
    private static final long serialVersionUID = 512L;

    private String name;
    private int age;
    //这里没有提供无参构造器
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    private void writeObject(java.io.ObjectOutputStream out) throws IOException {
        //将name实例变量值反转后写入二进制流
        out.writeObject(new StringBuffer(name).reverse());
        out.writeInt(age);
    }
    private void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        //将读取的字符串反转后赋给name实例变量
        this.name = ((StringBuffer) in.readObject()).reverse().toString();
        this.age = in.readInt();
    }
    private Object writeReplace() {
        ArrayList<Object> list = new ArrayList<>();
        list.add(name);
        list.add(age);
        return list;
    }
}
