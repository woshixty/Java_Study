package study_one;

public class Study_2 {

//	final double PI=3.14159;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        final double PI = 3.14159;
        byte i = 127;
        char j = 'a';
        char k = 97;
        String str = "XTY IS AHANDSOME BOY";

        System.out.println(j + i + k);

        int n1 = 100;
        double d1 = 123.456;

        System.out.println(n1 + d1);
//		System.out.println(String.format("%-5,%.2f", n1, d1));
//		System.out.printf("%05d,%.2f\n", n1, d1);
        System.out.printf("%c\n", j);

        boolean flag = true;

        if (true)
            if (flag)
                System.out.println(str);

        char ch = '\u0041';

        System.out.println(ch);
        if (Character.isLetter(ch))
            System.out.println(str);

        System.out.println(Integer.MAX_VALUE);

    }

}
