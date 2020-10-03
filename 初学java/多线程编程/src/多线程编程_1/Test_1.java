package 多线程编程_1;

public class Test_1 {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        MyThread mThread = new MyThread();
        mThread.start();
        for (int i = 0; i < 20; i++) {
            System.out.println("main" + i);
        }
    }
}
