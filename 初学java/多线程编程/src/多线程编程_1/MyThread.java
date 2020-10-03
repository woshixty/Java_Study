package 多线程编程_1;

public class MyThread extends Thread {

    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("run" + i);
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
