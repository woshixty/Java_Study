package test;

public class Test_2 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        Test_2_1 t1 = new Test_2_1(20, "cangLaoShi", 'B');
        t1.print();
        int i, j, k;
        for (i = 0; i < 50; i++) {
            for (j = 0; j < 100000000; j++)
                for (k = 0; k < 100; k++)
                    ;
            System.out.print('#');
        }
        System.out.print("100%");
    }

}
