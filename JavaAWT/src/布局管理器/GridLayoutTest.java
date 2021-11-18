package 布局管理器;

import java.awt.*;

public class GridLayoutTest {
    public static void main(String[] args) {
        Frame frame = new Frame("计算器");
        Panel panel1 = new Panel();
        panel1.add(new TextField(30));
        frame.add(panel1);
        Panel panel2 = new Panel();
        panel2.setLayout(new GridLayout(3, 5, 4, 4));
        String[] name = {
                "0", "1", "2", "3", "4", "5", "6",
                "7", "8", "9", "+", "-", "*", "/",
                "."
        };
        //向Panel中添加15个按钮
        for (int i = 0; i < name.length; i++) {
            Button button = new Button(name[i]);
            button.setBackground(Color.black);
            panel2.add(button);
        }
        frame.add(panel2);
        frame.pack();
        frame.setVisible(true);
    }
}
