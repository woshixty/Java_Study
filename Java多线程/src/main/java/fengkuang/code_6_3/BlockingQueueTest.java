package fengkuang.code_6_3;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/5
 **/
public class BlockingQueueTest {
    public static void main(String[] args) throws InterruptedException {
        //定义一个长度为2的阻塞队列
        BlockingQueue<String> bq = new ArrayBlockingQueue<>(2);
        //也可以用bq.add("Java");和bq.offer("Java");
        bq.put("Java");
        bq.put("Java");
        //此时会形成阻塞
        bq.put("Java");
    }
}
