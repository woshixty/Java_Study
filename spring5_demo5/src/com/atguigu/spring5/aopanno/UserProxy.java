package com.atguigu.spring5.aopanno;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/5
 **/
//增强的类
@Component
@Aspect
public class UserProxy {
    //前置通知
    //@Before表示作为前置通知（切入点表达式指明哪个类中的哪个方法）
    @Before(value = "execution(* com.atguigu.spring5.aopanno.User.add())")
    public void before() {
        System.out.println("before()......");
    }

    @After(value = "execution(* com.atguigu.spring5.aopanno.User.add())")
    public void after() {
        System.out.println("after()......");
    }

    @AfterReturning(value = "execution(* com.atguigu.spring5.aopanno.User.add())")
    public void afterReturning() {
        System.out.println("afterReturning()......");
    }

    @AfterThrowing(value = "execution(* com.atguigu.spring5.aopanno.User.add())")
    public void afterThrowing() {
        System.out.println("afterThrowing()......");
    }

    @Around(value = "execution(* com.atguigu.spring5.aopanno.User.add())")
    public void around(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        System.out.println("环绕之前......");
        //被增强的方法
        proceedingJoinPoint.proceed();
        System.out.println("环绕之后......");
    }
}
