package com.atguigu.spring5.test;

import com.atguigu.spring5.aopanno.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/5
 **/
public class TestAop {
    @Test
    public void testAopAnno() {
        ApplicationContext context = new ClassPathXmlApplicationContext("bean1.xml");
        User user = context.getBean("user", User.class);
        user.add();
    }
}
