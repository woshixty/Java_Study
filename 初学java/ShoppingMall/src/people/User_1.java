package people;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class User_1 {

    static final String name = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/RUNOOB?useSSL=false&serverTimezone=UTC";
    static String user = "";
    static String password = "";

    public static Connection conn = null;
    public static PreparedStatement pst = null;
    static ResultSet ret = null;

    public static void main(String[] args) throws SQLException {
        Scanner in = new Scanner(System.in);
        System.out.println("please input your name");
        user = in.next();
        System.out.println("please input your passwd");
        password = in.next();

        String sql = "SELECT * FROM info";

        try {
            Class.forName(name);// 指定连接类型
            conn = DriverManager.getConnection(url, user, password);// 获取连接
            System.out.println("connect successfully");
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("error");
        }

        pst = conn.prepareStatement(sql);// 准备执行语句
        ret = pst.executeQuery();// 执行语句，得到结果集
        ret.close();
        conn.close();
        pst.close();
        in.close();
    }

}
