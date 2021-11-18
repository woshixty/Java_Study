package 布局管理器;

import java.awt.*;

public class FlowLayoutTest {
    public static void main(String[] args) {
        Frame frame = new Frame("测试窗口");
        //设置Frame窗口使用FloatLayout布局管理器
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 5));
        //想窗口中添加10个按钮
        for (int i = 0; i < 10; i++) {
            frame.add(new Button("按钮" + 1));
        }
        //设置窗口为最佳大小
        frame.pack();
        frame.setVisible(true);
    }
}
