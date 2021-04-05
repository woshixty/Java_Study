package com.learn;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/19
 **/

public class testJdkProxy {
    public static void main(String[] args) {
        JdkProxyExample jdk = new JdkProxyExample();

        // 绑定关系，因为挂在接口 HelloWorld 下，所以声明代理对象 HelloWorld proxy
        HelloWorld proxy = (HelloWorld)jdk.bind(new HelloWorldImpl());

        // 注意，此时的HelloWorld对象已经是一个代理对象，它会进入代理的逻辑方法 invoke 里
        proxy.sayHelloWorld();
    }
}
