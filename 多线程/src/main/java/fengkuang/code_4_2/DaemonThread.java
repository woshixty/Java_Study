package fengkuang.code_4_2;

import fengkuang.code_4_1.JoinThread;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class DaemonThread extends Thread {
    //定义后台线程的线程执行体与普通线程没有多大区别
    @Override
    public void run() {
        for (int i = 0; i < 1000; i++) {
            System.out.println(getName() + " " + i);
        }
    }
    public static void main(String[] args) {
        //启动子线程
        DaemonThread t = new DaemonThread();
        //将此线程设置成后台线程
        t.setDaemon(true);
        //启动线程
        t.start();
        for (int i = 0; i < 10; i++) {
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
        //线程执行到此处，前台线程（main）结束，后台线程随之结束
        System.out.println("结束了");
    }
}
