package fengkuang.code_2_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/22
 **/
public class FirstThread extends Thread {
    private int i;

    //重写run方法
    @Override
    public void run() {
        super.run();
        for (i = 10; i < 100; i++) {
            //当线程类启动Thread时，直接使用this即可获取当前的线程
            //Thread的getName()方法返回当前线程的名字
            System.out.println(getName() + " " + i);
        }
    }

    public static void main(String[] args) {
        for (int i = 0; i < 100; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
            if (i == 20) {
                //创建并启动第一个线程
                new FirstThread().start();
                //创建并启动第二个线程
                new FirstThread().start();
            }
        }
    }
}
