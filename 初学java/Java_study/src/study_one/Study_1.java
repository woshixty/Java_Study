package study_one;

import java.util.*;

public class Study_1 {

    public static int max(int a, int b) {
        int ret;
        if (a > b) {
            ret = a;
        } else {
            ret = b;
        }
        return ret;
    }

    public static void print(String str) {
        System.out.println(str);
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner s = new Scanner(System.in);
        int a, b;
        String str;
        str = s.nextLine();
        print(str);
        a = s.nextInt();
        b = s.nextInt();
        s.close();
        System.out.println(max(a, b));
    }

}
