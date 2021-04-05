package com.learn.Interceptor;

import java.lang.reflect.Method;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/20
 * 拦截器
 **/

public interface Interceptor {

    /**
     * 这里面定义了三个方法
     * 参数的意义为
     * @param proxy    代理对象
     * @param target    真实对象
     * @param method    方法
     * @param args    运行方法参数
     * @return
     */
    // before 方法返回 boolean 值，在调用真实对象之前调用 返回 true 时，反射真实对象的方法，返回 false 时，调用 around 方法
    public boolean before(Object proxy, Object target, Method method, Object[] args);

    // 在 before 方法返回 false 时调用 around 方法
    public void around(Object proxy, Object target, Method method, Object[] args);

    // 反射真实对象方法或者 around 方法执行之后，调用 after 方法
    public void after(Object proxy, Object target, Method method, Object[] args);

}
