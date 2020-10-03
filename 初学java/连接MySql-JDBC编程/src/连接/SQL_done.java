package 连接;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL_done {
    static final String name = "com.mysql.cj.jdbc.Driver";
    static final String url = "jdbc:mysql://localhost:3306/USER_1";

    static Connection conn = null;
    static Statement stmt = null;
    static ResultSet rs = null;
    int count = 0;

    protected void connect(String user, String passwd) {
        try {
            Class.forName(name);
            conn = DriverManager.getConnection(url, user, passwd);
            stmt = conn.createStatement();
        } catch (ClassNotFoundException e) {
            System.out.println("驱动错误");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("连接错误");
            e.printStackTrace();
        }
    }

    protected void closecon() {
        try {
            if (stmt != null)
                stmt.close();
            if (conn != null)
                conn.close();
        } catch (SQLException e) {
            System.out.println("关闭失败");
            e.printStackTrace();
        }
    }

    public int update(String user, String passwd, String sql) {
        count = 0;
        connect(user, passwd);
        try {
            count = stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closecon();
        return count;
    }

    public ArrayList<Arry> result(String user, String passwd, String sql) {
        ArrayList<Arry> list = new ArrayList<>();
        rs = null;
        connect(user, passwd);
        try {
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                Arry arry = new Arry(rs.getString(1), rs.getInt(2), rs.getString(3), rs.getInt(4));
                list.add(arry);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        closecon();
        return list;
    }

}
