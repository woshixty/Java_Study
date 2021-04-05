package com.learn;

import java.lang.reflect.InvocationTargetException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/19
 * java反射机制
 * 通过反射生成带有参数的构建方法
 **/

public class ReflectServiceImpl2 {

    private String name;

    public ReflectServiceImpl2(String name) {
        this.name = name;
    }

    public void sayHello(String name) {
        System.err.println("Hello "+name);
    }

    //通过反射方法构建 ReflectServiceImpl
    public ReflectServiceImpl2 getInstance() {
        ReflectServiceImpl2 object = null;
        try {

            //给类加载器注册了一个类 ReflectServiceImpl 的全限定名，然后通过 newInstance 初始化了一个对象
            object = (ReflectServiceImpl2) Class.
                    forName("com.learn.ReflectServiceImpl2").    //通过forName加载到类的加载器
                    getConstructor(String.class).    //参数可以为多个，这里可以理解为参数类型为string的构建方法
                    newInstance("张三");    //用反射机制来生成对象

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
}
