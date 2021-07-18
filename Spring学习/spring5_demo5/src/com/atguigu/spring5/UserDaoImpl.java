package com.atguigu.spring5;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/2
 **/
public class UserDaoImpl implements UserDao {
    @Override
    public int add(int a, int b) {
        System.out.println("add方法执行了......");
        return a + b;
    }

    @Override
    public String update(String id) {
        System.out.println("update方法执行了......");
        return id;
    }
}
