/**
 * @author qyyzxty@icloud.com
 * 2021/4/8
 **/
public class TestBeep {

    public static void main(String[] args) {
        int i = 0;
        for (i = 0; i < 5; i++) {
            java.awt.Toolkit.getDefaultToolkit().beep();
        }
    }

}
