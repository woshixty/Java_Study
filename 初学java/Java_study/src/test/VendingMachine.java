package test;

import java.util.*;

public class VendingMachine {

    int price = 80;
    int balance;
    int total;

    void showPrint() {
        System.out.println("Welcome");
    }

    void insertMoney() {
        System.out.println("How much?");
        Scanner s = new Scanner(System.in);
        balance = s.nextInt();
        s.close();
    }

    void showBalance() {
        System.out.println(balance);
    }

    void getFood() {
        if (balance >= price) {
            System.out.println("Here you are");
            balance = balance - price;
            total += price;
        } else
            System.out.println("Your money is not enough");
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
