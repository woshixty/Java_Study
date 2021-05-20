package com.ayguigu.spring5.testdemo;


import com.ayguigu.spring5.Book;
import com.ayguigu.spring5.Orders;
import com.ayguigu.spring5.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * @author qyyzxty@icloud.com
 * @data 2021/4/22
 **/
public class TestSpring5 {

    @Test
    public void testAdd() {
        //1、加载配置文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("bean1.xml");
        //2、获取配置创建的对象
        User user = context.getBean("user", User.class);
        System.out.println(user);
        user.add();
        /**
         * BeanFactory：Spring内部使用接口，不提供给开发人员使用
         * ApplicationContext：BeanFactory接口的子接口，提供更多清大的功能，一般由开发人员使用
         */
    }

    @Test
    public void testBook() {
        //1、加载配置文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("bean1.xml");
        //2、获取配置创建的对象
        Book book = context.getBean("book", Book.class);
        System.out.println(book.getBauthor());
    }

    @Test
    public void testOrders() {
        //1、加载配置文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("bean1.xml");
        //2、获取配置创建的对象
        Orders orders = context.getBean("orders", Orders.class);
        System.out.println(orders.getName());
    }
}