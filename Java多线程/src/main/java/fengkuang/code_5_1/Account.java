package fengkuang.code_5_1;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/23
 **/
public class Account {
    //封装账户编号、账户余额的两个成员变量
    private String accountNo;
    private Double balance;
    //构造器
    public Account(String accountNo, Double balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }
    //setter and getter
    public String getAccountNo() {
        return accountNo;
    }
    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }
    //根据accountNo重写 equals() 和 hashCode() 方法
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        } else if (obj != null && obj.getClass() == Account.class) {
            Account target = (Account) obj;
            return target.getAccountNo().equals(accountNo);
        } else {
            return false;
        }
    }
    //hashCode()方法用于返回字符串的哈希码
    @Override
    public int hashCode() {
        return accountNo.hashCode();
    }
}
