package com.ayguigu.spring5;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/19
 * 演示set方法注入属性
 **/
public class Book {

    //创建属性
    private String name;

    private String bauthor;

    public Book(String name, String bauthor) {
        this.name = name;
        this.bauthor = bauthor;
    }

    public Book() {

    }

    public String getName() {
        return name;
    }

    // 创建属性对应的set方法--set方法注入
    public void setName(String name) {
        this.name = name;
    }

    public String getBauthor() {
        return bauthor;
    }

    public void setBauthor(String bauthor) {
        this.bauthor = bauthor;
    }

    //构造方法注入
    public Book(String name) {
        this.name = name;
    }

    public static void main(String[] args) {
        Book book = new Book("Python");
        book.setName("Java");
    }
}
