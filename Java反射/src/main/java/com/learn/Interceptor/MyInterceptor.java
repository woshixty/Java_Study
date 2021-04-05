package com.learn.Interceptor;

import com.learn.HelloWorld;
import com.learn.HelloWorldImpl;

import java.lang.reflect.Method;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/20
 * 这是实现类
 * 实现了所有接口方法
 * 使用 JDK 动态代理
 * 就可以去实现这些方法在适当时的调用逻辑了
 **/

public class MyInterceptor implements Interceptor {

    public boolean before(Object proxy, Object target, Method method, Object[] args) {
        System.err.println("反射方法前逻辑");
        return false;    // 不反射被代理对象原有方法
    }

    public void around(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("取代了被代理对象的方法");
    }

    public void after(Object proxy, Object target, Method method, Object[] args) {
        System.out.println("反射方法后逻辑");
    }

    public static void main(String[] args) {
        HelloWorld proxy = (HelloWorld) InterceptorJdkProxy.bind(new HelloWorldImpl(),
                "com.learn.Interceptor.MyInterceptor");
        proxy.sayHelloWorld();
    }

}
