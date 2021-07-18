package fengkuang.code_4_3;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class SleepTest {
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + " " + i);
        }
    }
}
