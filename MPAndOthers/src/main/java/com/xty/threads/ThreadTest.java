package com.xty.threads;

import lombok.var;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/5
 **/
public class ThreadTest {
    public static final int DELAY=10;
    public static final int STEPS=100;
    public static final double MAX_AMOUNT=1000;

    static class Bank{
        Bank() { }
        public void transform() {
            System.out.println("Hello Thread");
        }
    }

    public static void main(String[] args) {
        var bank =new Bank();
        Runnable task1= () -> {
          try {
              for (int i=0; i<STEPS; i++) {
                  double amount=MAX_AMOUNT*Math.random();
                  bank.transform();
                  Thread.sleep( (int) (DELAY*Math.random()));
              }
          } catch (InterruptedException e) {
              e.printStackTrace();
          }
        };

        Runnable task2= () -> {
            try {
                for (int i=0; i<STEPS; i++) {
                    double amount=MAX_AMOUNT*Math.random();
                    bank.transform();
                    Thread.sleep( (int) (DELAY*Math.random()));
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };

        new Thread(task1).start();
        new Thread(task2).start();
    }
}
