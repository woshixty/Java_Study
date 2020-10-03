package 连接;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class LINK {

    public static void main(String[] args) throws Exception {
        // 1.导入jar包
        // 2.注册驱动

        // Class.forName("com.mysql.cj.jdbc.Driver");

        // 3.获取数据库连接对象
        Connection conn = DriverManager.getConnection(
                "jdbc:mysql://localhost:3306/USER_1", "root", "11111111");
        System.out.println("yes");

        // 4.定义sql语句
        String sql = "update info set money =15000 where id=1";
        System.out.println("yes");

        // 5.获取sql执行对象 statement
        Statement stmt = conn.createStatement();
        System.out.println("yes");

        // 6.执行sql
        int count = stmt.executeUpdate(sql);
        System.out.println("yes");

        // 7.处理结果
        System.out.println(count);
        System.out.println("yes");

        // 8.释放资源
        conn.close();
        stmt.close();
        System.out.println("yes");
    }

}
