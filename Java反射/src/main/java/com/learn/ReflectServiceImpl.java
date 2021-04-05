package com.learn;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/19
 * java反射机制
 * 反射生成对象
 **/

public class ReflectServiceImpl {

    public void sayHello(String name) {
        System.err.println("Hello "+name);
    }

    //通过反射方法构建 ReflectServiceImpl
    public ReflectServiceImpl getInstance() {
        ReflectServiceImpl object = null;
        try {
            //给类加载器注册了一个类 ReflectServiceImpl 的全限定名，然后通过 newInstance 初始化了一个对象
            object = (ReflectServiceImpl) Class.forName("com.learn.ReflectServiceImpl").newInstance();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return object;
    }

    //反射方法获取方法对象
    public Object reflectMethod() {
        Object returnObj = null;
        ReflectServiceImpl target = new ReflectServiceImpl();

        try {

            Method method = ReflectServiceImpl.class.getMethod("sayHello", String.class);
            returnObj = method.invoke(target, "张三");

        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return returnObj;
    }

    //反射生成对象和反射调度方法
    public static Object reflect() {
        ReflectServiceImpl object = null;
        try {

            //生成对象
            object = (ReflectServiceImpl)
                    Class.forName("com.learn.ReflectServiceImpl").newInstance();

            //生成方法
            Method method = object.getClass().getMethod("sayHello", String.class);
            method.invoke(object,"张三");    //反射方法，第一个参数为target，确定哪个对象调用方法，而张三是参数，这就等于target.sayHello()

        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return object;
    }

    public static void main(String[] args) {
        ReflectServiceImpl reflectService = (ReflectServiceImpl) reflect();
    }
}
