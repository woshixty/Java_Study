package com.learn.Interceptor;

import org.omg.PortableInterceptor.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/20
 * 在 JDK 动态代理中使用拦截器
 **/

/**
 * 代码执行步骤
 * 1. 在 bind 方法中，在这个方法中使用 JDK 动态代理绑定了一个对象，然后返回代理对象
 * 2. 如果没有设置拦截器，则反射真实对象的方法，然后结束，否则进行第三步
 * 3. 通过反射生成拦截器，并准备使用它
 * 4. 调用拦截器的 before 方法，如果返回为 true 反射原来的方法，否则运行拦截器的 around 方法
 * 5. 调用拦截器 after 方法
 * 6. 返回结果
 */
public class InterceptorJdkProxy implements InvocationHandler {

    private Object target;    // 真实对象
    private String interceptorClass;    // 拦截器全限定名

    public InterceptorJdkProxy(Object target, String interceptorClass) {
        this.target = target;
        this.interceptorClass = interceptorClass;
    }


    /**
     * 绑定委托对象并返回一个【代理占位】
     * @param target    真实对象
     * @param interceptorClass    拦截器的全限定名
     * @return
     */
    public static Object bind(Object target, String interceptorClass) {
        // 取得代理对象
        return Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                new InterceptorJdkProxy(target, interceptorClass)
                );
    }

    /**
     * 通过代理对象调用方法，首先进入这个方法
     * @param proxy    代理对象
     * @param method    方法，被调用方法
     * @param args    方法的参数
     * @return
     * @throws Throwable
     */
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        if(interceptorClass == null) {
            // 没有设置拦截器则直接反射原方法
            return method.invoke(target, args);
        }
        Object result = null;

        // 通过反射生成拦截器
        MyInterceptor interceptor = (MyInterceptor) Class.forName(interceptorClass).newInstance();

        // 调用前置方法
        if (interceptor.before(proxy, target, method, args)) {

            // 反射原有方法
            result = method.invoke(target, args);
        } else {
            // 返回 false 就执行 around 方法
            interceptor.around(proxy, target, method, args);
        }

        // 调用后置方法
        interceptor.after(proxy, target, method, args);
        return result;
    }
}