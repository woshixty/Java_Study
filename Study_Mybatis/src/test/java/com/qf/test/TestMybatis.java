package com.qf.test;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import com.qf.dao.UserDAO;
import com.qf.entity.User;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/7/11
 **/
public class TestMybatis {
    public static void main(String[] args) throws IOException {

        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        System.out.println("1");

        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        System.out.println("2");

        SqlSession sqlSession = sqlSessionFactory.openSession();
        System.out.println("3");

        UserDAO mapper = sqlSession.getMapper(UserDAO.class);
        System.out.println("4");

        User user1 = mapper.queryUserById(1);
        System.out.println("5");

        System.out.println(user1);

    }
}
