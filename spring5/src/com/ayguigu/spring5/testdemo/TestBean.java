package com.ayguigu.spring5.testdemo;


import com.ayguigu.spring5.bean.Emp;
import com.ayguigu.spring5.service.UserService;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/4/22
 **/
public class TestBean {

    @Test
    public void testAdd() {
        //1、加载配置文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("bean2.xml");
        //2、获取配置创建的对象
        UserService userService = context.getBean("userService", UserService.class);
        userService.add();
    }

    @Test
    public void testBean2() {
        //1、加载配置文件
        ApplicationContext context = new
                ClassPathXmlApplicationContext("bean3.xml");
        //2、获取配置创建的对象
        Emp emp = context.getBean("emp", Emp.class);
        System.out.println(emp);
    }
}