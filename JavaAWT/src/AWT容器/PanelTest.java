package AWT容器;

import java.awt.*;

public class PanelTest {
    public static void main(String[] args) {
        Frame frame = new Frame("测试窗口");
        //创建一个Panel容器
        Panel panel = new Panel();
        //向Panel容器中添加两个组件
        panel.add(new TextField(20));
        panel.add(new Button("单击我"));
        frame.setBounds(30, 30, 300, 150);
        //将Panel容器添加到Frame窗口中
        frame.add(panel);
        frame.setVisible(true);
    }
}
