package fengkuang.code_5_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class DrawTest {
    public static void main(String[] args) {
        //创建一个账户
        Account acct = new Account("123", 1000.0);
        //模拟两个线程向同一账户取钱
        new DrawThread("甲", acct, 800).start();
        new DrawThread("乙", acct, 800).start();
    }
}
