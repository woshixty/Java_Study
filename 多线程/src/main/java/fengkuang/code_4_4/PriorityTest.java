package fengkuang.code_4_4;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class PriorityTest extends Thread {
    //定义一个有参构造器，用于创建线程时指定名称
    public PriorityTest(String name) {
        super(name);
    }
    //线程执行体
    @Override
    public void run() {
        super.run();
        for (int i = 0; i< 500; i++) {
            System.out.println(getName() + "的优先级为：" + getPriority() + "，循环变量值为" + i);
        }
    }
    public static void main(String[] args) {
        //设置主线程的优先级
        Thread.currentThread().setPriority(6);
        for (int i = 0; i < 30; i++) {
            if (i == 10) {
                PriorityTest low = new PriorityTest("低级");
                low.start();
                System.out.println("创建之初的线程优先级：" + low.getPriority());
                //设置该线程为最低优先级
                low.setPriority(Thread.MIN_PRIORITY);
            }
            if (i == 20) {
                PriorityTest high = new PriorityTest("高级");
                high.start();
                System.out.println("创建之初的线程优先级：" + high.getPriority());
                //设置该线程为最高优先级
                high.setPriority(Thread.MAX_PRIORITY);
            }
        }
    }
}
