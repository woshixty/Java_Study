package com.atguigu.spring5;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/2
 **/
public class JDKProxy {
    public static void main(String[] args) {
//        //创建接口实现类代理对象
//        Class[] interfaces = {UserDao.class};
//        Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new InvocationHandler() {
//            @Override
//            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
//                return null;
//            }
//        });
        //创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
        UserDao userDao = new UserDaoImpl();
        UserDao dao = (UserDao) Proxy.newProxyInstance(JDKProxy.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        int result = dao.add(1, 2);
        System.out.println(result);
    }
}

//创建代理对象代码
class UserDaoProxy implements InvocationHandler {
    private Object object;
    //1、创建的是谁的代理对象，就将谁传进来
    //有参构造传递参数
    public UserDaoProxy(Object obj) {
        this.object = obj;
    }
    //2、

    //写我们要增强的操作逻辑
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //方法之前
        System.out.println("方法之前执行......" + method.getName() + "：传递的参数是" + Arrays.toString(args));
        //被增强的方法
        Object res = method.invoke(object, args);
        //方法之后
        System.out.println("方法之前执行......" + object);
        return res;
    }
}
