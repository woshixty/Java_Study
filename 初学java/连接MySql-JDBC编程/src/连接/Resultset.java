package 连接;

import java.sql.SQLException;
import java.util.ArrayList;

public class Resultset {
    static String user = "root";
    static String passwd = "11111111";

    public static void main(String[] args) throws SQLException {
        SQL_done sq = new SQL_done();
        String sql = "insert into info values('hxx',13000,'3950x',12)";
//		sq.update("root", "11111111", sql);
        System.out.println(sq.update("root", "11111111", sql));
        sql = "select * from info";
        ArrayList<Arry> list = new ArrayList<>();
        list = sq.result(user, passwd, sql);
//		for (Arry arry : list) { 
//			System.out.println(arry.toString());
//		}
        System.out.println(list);
    }
}
