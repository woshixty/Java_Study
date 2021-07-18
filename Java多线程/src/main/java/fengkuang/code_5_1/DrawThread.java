package fengkuang.code_5_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class DrawThread extends Thread {
    //模拟用户账户
    private Account account;
    //当前取钱线程所希望取得钱数
    private double drawAmount;
    public DrawThread(String name, Account account, double drawAmount) {
        super(name);
        this.account = account;
        this.drawAmount = drawAmount;
    }
    //当多个线程修改同一共享数据时，将涉及到数据安全问题
    @Override
    public void run() {
        super.run();
        //使用account作为同步监视器，任何线程在进入下面的代码块之前，必须先获得对account对象的锁定，其他对象无法获得锁，也就无法修改它
        //"加锁-修改-释放锁"的步骤
        synchronized (account) {
            //账户余额大于取钱数目
            if (account.getBalance() >= drawAmount) {
                //吐出钞票
                System.out.println(getName() + " " + drawAmount);
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //修改余额
                account.setBalance(account.getBalance() - drawAmount);
                System.out.println("余额为：" + account.getBalance());
            } else {
                System.out.println("取钱失败！余额不足！");
            }
        }
    }
}
