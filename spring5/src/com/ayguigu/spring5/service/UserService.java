package com.ayguigu.spring5.service;

import com.ayguigu.spring5.dao.UserDao;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/5/20
 **/
public class UserService {

    //创建UserDao类型属性，生成SET方法
    private UserDao userDao;
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    public void add() {
        System.out.println("Service add ....");
        userDao.update();
    }

}
