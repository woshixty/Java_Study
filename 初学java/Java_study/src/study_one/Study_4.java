package study_one;

import java.util.Scanner;

public class Study_4 {

    public static void main(String args[]) {
        outer:
        for (int i = 1; i < 5; i++) {
            for (int j = 1; j < 5; j++) {
                if (j > i) {
                    System.out.println();
                    continue outer;
                }
                System.out.print(" " + (i * j));
            }
            System.out.println();
        }
        Scanner s = new Scanner(System.in);
        String str = s.next();
        double a = -123.123;
        a = Math.abs(a);
        System.out.println(a);
        StringBuffer str1 = new StringBuffer();
        str1.append("XTY IS A GOOD BOY");
        str1.delete(2, 3);
        System.out.println(str1);
    }
}
