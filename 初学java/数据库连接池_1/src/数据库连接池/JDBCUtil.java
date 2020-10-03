package 数据库连接池;

import java.io.InputStream;
import java.sql.Connection;
import java.util.Properties;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

public class JDBCUtil {
import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

    public static void main() throws Exception {
        // 创建数据库连接池对象
        Properties pro = new Properties();
        InputStream is = JDBCUtil.class.getClassLoader().getResourceAsStream("druid.properties");
        pro.load(is);
        DataSource ds = DruidDataSourceFactory.createDataSource(pro);
        // 获取连接对象
        Connection conn = ds.getConnection();
        System.out.println(conn);
    }
}