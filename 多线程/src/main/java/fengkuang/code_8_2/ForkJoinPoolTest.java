package fengkuang.code_8_2;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.TimeUnit;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/2
 **/
//继承RecursiveAction来实现"可分解"的任务
class PrintTask extends RecursiveAction {
    //每个小任务最多只打印50个数
    private static final int THRESHOLD = 50;
    //开始和结束
    private int start,end;
    //打印从start到end的任务
    public PrintTask(int start, int end) {
        this.end = end;
        this.start = start;
    }
    //总的大任务
    @Override
    protected void compute() {
        //当end和start之间的差距小于THRESHOLD时开始打印
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                System.out.println(Thread.currentThread().getName() + "值为" + i);
            }
        } else {
            //当end和start之间的差距大于THRESHOLD时，将大任务分解成小任务
            int middle = (start + end) / 2;
            PrintTask left = new PrintTask(start, middle);
            PrintTask right = new PrintTask(middle, end);
            //执行两个小任务
            left.fork();
            right.fork();
        }
    }
}

public class ForkJoinPoolTest {
    public static void main(String[] args) throws InterruptedException {
        ForkJoinPool pool = new ForkJoinPool();
        //提交可分解的PrintTask任务
        pool.submit(new PrintTask(0, 300));
        pool.awaitTermination(2, TimeUnit.SECONDS);
        //关闭线程池
        pool.shutdown();
    }
}
