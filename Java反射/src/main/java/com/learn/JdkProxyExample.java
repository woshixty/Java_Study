package com.learn;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/19
 * 要实现代理逻辑类必须去实现 java.lang.reflect.InvocationHandler 接口，里边定义了一个 invoke 方法并提供接口数组用于下挂代理对象
 **/

public class JdkProxyExample implements InvocationHandler {

    //真实对象
    private Object target = null;


    /**
     * 代理方法逻辑
     * @param proxy    代理对象
     * @param method    当前调度方法
     * @param args    当前方法参数
     * @return    代理结果返回
     * @throws Throwable    异常
     */
    // invoke 方法并提供接口数组用于下挂代理对象
    // 实现代理逻辑方法
    public Object invoke(Object proxy,    // 代理对象，就是 bind 方法生成的对象
                         Method method,    // 当前调度的方法
                         Object[] args    // 调度方法的参数
    ) throws Throwable {

        System.out.println("进入代理逻辑方法");
        System.out.println("在调度真实对象之前的服务");

        // 我们使用了代理对象调度方法后，它会进入到 invoke 方法里面去
        // 这段代码相当于调度真实对象的方法，只是通过反射实现而已
        Object obj = method.invoke(target, args);    //相当于调用 sayHelloWorld 方法

        /**
         * 类比之前的例子
         * proxy 相当于商务对象
         * target 相当于软件工程师
         * bind 方法就是建立商务和软件工程师代理关系的方法
         * 而 invoke 就是商务逻辑，它将控制软件工程师的访问
         */

        System.out.println("在调度真实对象之后的服务");
        return obj;
    }

    /**
     * 建立代理对象和真实对象的代理关系，并返回代理对象
     * @param target 真实对象
     * @return 代理对象
     */
    public Object bind(Object target) {
        this.target = target;
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),    // 类加载器，采用了target本身的的类加载器
                target.getClass().getInterfaces(),    // 把生成的动态代理对象挂在哪些接口下，这里表示放在target实现的接口下 即HelloWorld，代理对象可以这样声明：HelloWorld proxy = xxxx;
                this);    //定义 实现方法逻辑 的 代理类，this 表示当前对象，它必须实现 InvocationHandler 的 invoke 方法，它就是代理逻辑方法的现实方法
    }

    //测试jdk动态代理
    public void testJdkProxy() {
        JdkProxyExample jdk = new JdkProxyExample();

        // 绑定关系，因为挂在接口 HelloWorld 下，所以声明代理对象 HelloWorld proxy
        HelloWorld proxy = (HelloWorld)jdk.bind(new HelloWorldImpl());

        // 注意，此时的HelloWorld对象已经是一个代理对象，它会进入代理的逻辑方法 invoke 里
        proxy.sayHelloWorld();
    }

}
