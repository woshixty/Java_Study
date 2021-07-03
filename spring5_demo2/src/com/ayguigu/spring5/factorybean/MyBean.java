package com.ayguigu.spring5.factorybean;

import com.ayguigu.spring5.collectiontype.Course;
import org.springframework.beans.factory.FactoryBean;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/24
 **/
public class MyBean implements FactoryBean<Course> {

    //返回定义bean
    @Override
    public Course getObject() throws Exception {
        Course course = new Course();
        course.setCname("abc");
        return course;
    }

    @Override
    public Class<?> getObjectType() {
        return null;
    }
}
