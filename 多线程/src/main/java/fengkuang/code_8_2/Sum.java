package fengkuang.code_8_2;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/2
 **/
//继承RecursiveTask来实现"可分解"的任务
class CalTask extends RecursiveTask<Integer> {
    //每个小任务最多只打印20个数
    private static final int THRESHOLD = 20;
    //开始和结束
    private int start,end;
    //数组
    int arr[];
    //打印从start到end的任务
    public CalTask(int[] arr, int start, int end) {
        this.arr = arr;
        this.end = end;
        this.start = start;
    }
    //总的大任务
    @Override
    protected Integer compute() {
        int sum = 0;
        //当end和start之间的差距小于THRESHOLD时开始累加
        if (end - start < THRESHOLD) {
            for (int i = start; i < end; i++) {
                sum += arr[i];
            }
            return sum;
        } else {
            //当end和start之间的差距大于THRESHOLD时，将大任务分解成小任务
            int middle = (start + end) / 2;
            CalTask left = new CalTask(arr, start, middle);
            CalTask right = new CalTask(arr, middle, end);
            //执行两个小任务
            left.fork();
            right.fork();
            //把两个小任务的累加结果合并起来
            return left.join() + right.join();
        }
    }
}

public class Sum {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = new int[100];
        Random rand = new Random();
        int total = 0;
        //初始化100个数字元素
        for (int i = 0, len = arr.length; i < len; i++) {
            int tmp = rand.nextInt(20);
            //对数组元素赋值
            total += (arr[i] = tmp);
        }
        System.out.println(total);
        //创建一个通用池
        ForkJoinPool pool = ForkJoinPool.commonPool();
        //提交可分解的CalTask任务
        Future<Integer> future = pool.submit(new CalTask(arr, 0, arr.length));
        System.out.println(future.get());
        //关闭线程池
        pool.shutdown();
    }
}
