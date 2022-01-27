package fengkuang.code_7_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/6
 **/
//定义自己的异常处理器
class MyExHandler implements Thread.UncaughtExceptionHandler {
    //实现uncaughtException()方法，该方法将处理线程的未处理异常
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.out.println(t + "线程出现了异常：" + e);
    }
}
public class ExHandler {
    public static void main(String[] args) {
        //设置主线程的异常处理器
        Thread.currentThread().setUncaughtExceptionHandler(new MyExHandler());
        var a = 5 / 0;
        System.out.println("程序正常结束！");
    }
}
