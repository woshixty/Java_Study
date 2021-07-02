package fengkuang.code_6_2;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/26
 **/
public class DepositThread extends Thread {
    //模拟用户账户
    private Account account;
    //当前取钱线程所希望的线程
    private double drawAmount;
    public DepositThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }
    //重复100次存钱取钱操作
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            account.deposit(drawAmount);
        }
    }
}
