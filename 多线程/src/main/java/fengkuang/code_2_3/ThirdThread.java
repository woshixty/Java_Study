package fengkuang.code_2_3;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/22
 **/
public class ThirdThread {
    public static void main(String[] args) {
        //先使用Lambda表达式创建Callable<Integer>对象
        //使用FutureTask来包装Callable对象
        FutureTask<Integer> task = new FutureTask<>( (Callable<Integer>)() -> {
                    int i = 0;
                    for (; i < 100; i++) {
                        System.out.println(Thread.currentThread().getName() + " " + i);
                    }
                    //call()可以有返回值
                    return i;
                });
        for (int i = 0; i <100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 20) {
                //实质还是以Callable对象来创建并启动线程的
                new Thread(task, "有返回值的线程").start();
            }
        }
        //需等线程结束后才能获得值
        try {
            //获取线程的返回值
            System.out.println("子线程的返回值" + task.get());
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
