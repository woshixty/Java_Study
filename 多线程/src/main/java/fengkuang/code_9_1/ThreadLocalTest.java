package fengkuang.code_9_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/2
 **/
class Account {
    //定义一个ThreadLocal类型的变量，该变量将是一个线程局部变量
    //每个线程将会保留该变量的一个副本
    private ThreadLocal<String> name = new ThreadLocal<>();
    //定义一个初始化name成员变量的构造器
    public Account(String str) {
        this.name.set(str);
        //下面代码用于访问当前线程的name副本的值
        System.out.println("---" + this.name.get());
    }
    //name的setter和getter方法
    public String getName() {
        return this.name.get();
    }
    public void setName(String str) {
        this.name.set(str);
    }
}

class MyTest extends Thread {
    //定义一个Account类型的成员变量
    private Account account;
    public MyTest(Account account, String name) {
        super(name);
        this.account = account;
    }
    public void run() {
        //循环10次
        for(var i = 0; i < 10; i++) {
            //当i==6时输出账户名代替换成当前线程名
            if(i == 6) {
                account.setName(getName());
            }
            //输出同一个账户的账户名称和循环变量
            System.out.println(account.getName() + "账户的i值" + i);
        }
    }
}
public class ThreadLocalTest {
    public static void main(String[] args) {
        //启动两个线程，两个线程共享一个Account
        var at = new Account("初始名");
        /**
         * 虽然两个线程共享一个账户，但是只有一个账户名
         * 但由于账户名是ThreadLocal类型的，所以每个线程
         * 都完全拥有各自的账本账户名副本，因此在i==6之后将看到
         * 两个线程访问同一个账户时出现两个不同的账户名
         */
        new MyTest(at, "线程甲").start();
        new MyTest(at, "线程乙").start();
    }
}
