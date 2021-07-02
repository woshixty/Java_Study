package chapter1;

/**
 * @author qyyzxty@icloud.com
 * 2021/5/21
 **/
public class Test1 {
    public static void main(String[] args) {
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
