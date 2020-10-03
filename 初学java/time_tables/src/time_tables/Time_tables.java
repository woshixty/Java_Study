package time_tables;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class Time_tables {

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        JFrame frame = new JFrame();
        JTable table = new JTable(new Time_tables_Data());
        JScrollPane pane = new JScrollPane(table);
        frame.add(pane);
//		frame.add(table);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

    }
}
