package com.xty.threads;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/5
 **/
public class Bank {
    private final double[] accounts;

    public Bank(double[] accounts) {
        this.accounts = accounts;
    }

    public Bank(int n, double initialBalance) {
        accounts = new double[n];
        Arrays.fill(accounts, initialBalance);
    }

    public void transfer(int from, int to, double amount) {
        if(accounts[from]<amount) return;
        System.out.println(Thread.currentThread());
        accounts[from]-=amount;
        System.out.printf("%10.2f from %d to %d", amount, from, to);
        accounts[to]+=amount;
        System.out.printf("total balance: %10.2f %n", getTotalBalance());
    }

    private double getTotalBalance() {
        double sum=0;
        for (double a: accounts)
            sum+=a;
        return sum;
    }

    public int size() {
        return accounts.length;
    }
}
