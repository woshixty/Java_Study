package jdbc_utils;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Jdbc 工具类
 *
 * @author mr.stark
 */

public class JDBCUtils {
    public static Connection getConnection() {

        return null;
    }

    public static void close(Statement stmt, Connection conn) {
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn != null) {
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub

    }

}
