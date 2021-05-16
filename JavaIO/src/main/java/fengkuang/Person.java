package fengkuang;

import lombok.Data;

import java.io.Serializable;
/**
 * @author qyyzxty@icloud.com
 * 2021/5/16
 **/
@Data
public class Person implements Serializable {
    private String name;
    private int age;
    //这里没有提供无参构造器
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }
}
