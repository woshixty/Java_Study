package fengkuang.code_6_2;

import java.util.Objects;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author qyyzxty@icloud.com
 * 2021/6/5
 **/
public class Account {
    //显示定义Lock对象
    private final Lock lock = new ReentrantLock();
    //获得指定Lock对象对应的Condition
    private final Condition cond = lock.newCondition();
    //封装账户编号、账户余额两个成员变量
    private String accountNo;
    private double balance;
    //标识账户中是否已有存款的旗标
    private boolean flag = false;
    //构造器
    public Account() {
    }
    public Account(String accountNo, double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }
    //getter 和 setter方法
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public double getBalance() {
        return balance;
    }
    //取钱操作
    public void draw(double drawAmount) {
        //加锁
        lock.lock();
        try {
            //如果flag为假，表明账户中还没有人存钱进去，取钱方法阻塞
            if(!flag) {
                cond.await();
            } else {
                //执行取钱操作
                System.out.println(Thread.currentThread().getName() + "取钱：" + drawAmount);
                balance -= drawAmount;
                System.out.println("账户余额为：" + balance);
                //将标识账户是否有存款的标志设为false
                flag = false;
                //唤醒其他线程
                cond.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    //存钱操作
    public void deposit(double depositAmount) {
        //加锁
        lock.lock();
        try {
            //如果flag为真，表明账户中已经有人存钱进去，存钱方法阻塞
            if(flag) {
                cond.await();
            } else {
                //执行存钱操作
                System.out.println(Thread.currentThread().getName() + "存钱：" + depositAmount);
                balance += depositAmount;
                System.out.println("账户余额为：" + balance);
                //将标识账户是否有存款的标志设为true
                flag = true;
                //唤醒其他线程
                cond.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
    //hashCode()和equals()方法
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Double.compare(account.balance, balance) == 0 && flag == account.flag && Objects.equals(lock, account.lock) && Objects.equals(cond, account.cond) && Objects.equals(accountNo, account.accountNo);
    }
    @Override
    public int hashCode() {
        return Objects.hash(lock, cond, accountNo, balance, flag);
    }
}
