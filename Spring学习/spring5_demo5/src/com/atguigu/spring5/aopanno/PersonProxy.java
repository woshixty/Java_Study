package com.atguigu.spring5.aopanno;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/8
 **/
@Component
@Aspect
@Order(1)
public class PersonProxy {
    //后置通知（返回通知）
//    @Before(value = "")
}
