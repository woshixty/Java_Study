package homework_1;

import java.util.Scanner;

public class No_1 {
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Scanner in = new Scanner(System.in);
        int n;
        int i, j;
        System.out.println("请输入整数n");
        n = in.nextInt();
        for (i = 1; i <= n; i++) {
            for (j = n - i; j > 0; j--)
                System.out.print(" ");
            for (j = 0; j < i; j++)
                System.out.print(i);
            System.out.println();
        }
        in.close();
    }
}
