package fengkuang.code_6_1;

import lombok.Data;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/26
 **/
@Data
public class Account {
    //封装账户编号、账户余额
    private String accountNo;
    private double balance;
    //标识账户中是否有存款
    private boolean flag = false;
    //构造器
    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }
    //因为账户余额不允许修改，因此只提供getter方法
    public double getBalance() {
        return this.balance;
    }
    public synchronized void draw(double drawAmount) {
        try {
            //如果flag为假，表明账户中没有人存钱进去，取钱方法阻塞
            if (!flag) {
                wait();
            } else {
                //执行取钱操作
                System.out.println(Thread.currentThread().getName() + "取钱：" + drawAmount);
                balance -= drawAmount;
                System.out.println("账户余额为：" + balance);
                //将标识账户是否已有存款的旗标设为false
                flag = false;
                //唤醒其他线程
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public synchronized void deposit(double depositAmount) {
        try {
            //如果flag为真，表明账户中已经有人存钱进去，存钱方法阻塞
            if (flag) {
                wait();
            } else {
                //执行存钱操作
                System.out.println(Thread.currentThread().getName() + "存钱：" + depositAmount);
                balance += depositAmount;
                System.out.println("账户余额为：" + balance);
                //将标识账户是否已有存款的旗标设为true
                flag = true;
                //唤醒其他线程
                notifyAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
