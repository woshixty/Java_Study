package 连接;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class 练习 {

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {
        try {
            // 1. 注册驱动
            Class.forName("com.mysql.cj.jdbc.Driver");

            // 2.定义sql
            String sql = "insert into info values ('jhl',7000,'3900x',4)";

            // 3.获取connecti对象
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/USER_1", "root", "11111111");

            // 4.获取执行sql对象
            stmt = conn.createStatement();

            // 5.执行sql语句
            int count = stmt.executeUpdate(sql);

            sql = "update info set money=50000 where id=1";

            stmt.executeUpdate(sql);

            sql = "select * from info";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()) {
                System.out.println(rs.getString(1) + ":" + rs.getInt(2) + "$");
            }

            System.out.println(count);
        } catch (ClassNotFoundException e) {
            System.out.println("未找到驱动");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("获取对象失败");
            e.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                System.out.println("关闭失败");
                e.printStackTrace();
            }
        }

    }

}
