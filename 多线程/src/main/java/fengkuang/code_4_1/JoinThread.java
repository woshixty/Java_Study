package fengkuang.code_4_1;

import lombok.SneakyThrows;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class JoinThread extends Thread {
    //提供一个带参数的构造器，用于设置该线程的名称
    public JoinThread(String name) {
        super(name);
    }
    //重写run()方法，定义线程执行体
    @SneakyThrows
    @Override
    public void run() {
        super.run();
        for (int i = 0; i < 100; i++) {
            System.out.println(getName() + " " + i);
            if (i == 30)
                Thread.sleep((long) 0.1);
        }
    }
    public static void main(String[] args) throws InterruptedException {
        //启动子线程
        new JoinThread("新线程").start();
        for (int i = 0; i < 100; i++) {
            if (i == 20) {
                Thread jt = new JoinThread("被join的线程");
                jt.start();
                //main()调用了jt线程的join方法，main(线程)，必须等到jt执行结束才会向下执行
                jt.join();
            }
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}
