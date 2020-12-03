package com.qf;

import com.qf.DAO.HelloDAO;
import com.qf.entity.Hello;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/21
 **/

public class TestMybaits {
    public static void main(String[] args) throws IOException {
        // Mybaits API

        // 1.加载配置文件
        InputStream inputStream = Resources.getResourceAsStream("mybaits-config.xml");

        // 2.构建sqlSessionFactory
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // 3.通过SqlSessionFactory 创建 sqlSession
        SqlSession sqlSession = sqlSessionFactory.openSession();

        // 4.通过SqlSession 获得 DAO 实现类的对象
        HelloDAO mapper = sqlSession.getMapper(HelloDAO.class);  //获取 UserDAO 对应实现类的对象

        Hello hello = new Hello("你好", null);
        mapper.insert(hello);
        System.out.println(hello);

        // 5.测试查询方法
        System.out.println(mapper.selectByName("谢庭宇"));

        sqlSession.commit();
    }
}
