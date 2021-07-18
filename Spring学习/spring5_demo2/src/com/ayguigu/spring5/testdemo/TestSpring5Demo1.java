package com.ayguigu.spring5.testdemo;

import com.ayguigu.spring5.collectiontype.Course;
import com.ayguigu.spring5.collectiontype.Stu;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/22
 **/
public class TestSpring5Demo1 {

    @Test
    public void testCollection() {
        ApplicationContext context  = new ClassPathXmlApplicationContext("bean1.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu);
    }

    @Test
    public void testListObject() {
        ApplicationContext context  = new ClassPathXmlApplicationContext("bean1.xml");
        Stu stu = context.getBean("stu", Stu.class);
        System.out.println(stu.getCourseList());
        for (Course cours : stu.getCourseList()) {
            System.out.println(cours);
        }
    }

}
