package study_one;

public class Study_3 {

//	String STR = "XTY IS A HANDSOME BOY";

    public static void main(String[] args) {
        String STR = "XTY IS A HANDSOME BOY";
        // TODO Auto-generated method stub
        int i = Integer.parseInt("123");

        double d = Double.parseDouble("123.123");
        System.out.println(i);
        System.out.println(d);

        int j = Integer.valueOf("123").intValue();
        String s = Integer.toString(123);
        String ss = "" + 123.123;
        System.out.println(ss);

        char[] c1 = {'a', 'b', 'c'};
        String str1 = new String(c1);
        String str2 = "abc";
        char[] c2 = str2.toCharArray();

        System.out.println(str1 + " " + str2);
        System.out.print(c1);
        System.out.print(c2[1]);
        System.out.printf("\n");

        str1 = str1.toUpperCase();
        System.out.println(str1 + " " + str2);

        if (str1.equals("ABC"))
            System.out.println(STR);

        int position = 0;
        String path = "c:\\java\\jsp\\A.java";
        // 获取path中最后出现目录分隔符号的位置
        position = path.lastIndexOf("\\");
        System.out.println("c:\\java\\jsp\\A.java中最后出现\\的位置:" + position);

    }

}
