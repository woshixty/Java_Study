package com.atguigu.spring5.aopanno;

import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/5
 **/
//被增强的类
@Component
public class User {
    public void add() {
        System.out.println("add()......");
    }
}
