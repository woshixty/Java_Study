package homework_1;

import java.util.Scanner;

public class No_2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);
        StringBuffer s1 = new StringBuffer();
        System.out.println("请输入一个字符串");
        String s2 = in.nextLine();
        if (s2.equals(s1.append(s2).reverse().toString()))
            System.out.print("是回文数");
        else
            System.out.print("不是回文数");
        in.close();
    }
}
