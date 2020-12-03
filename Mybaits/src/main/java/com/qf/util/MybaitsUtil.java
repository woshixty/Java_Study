package com.qf.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.security.PublicKey;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/22
 * 1.加载配置
 * 2.创建SqlSessionFactory
 * 3.创建Session
 **/

public class MybaitsUtil {

    private static SqlSessionFactory  sqlSessionFactory;

    // 线程唯一 全局不唯一
    // 创建ThreadLocal绑定当前线程中的SqlSession对象
    private static final ThreadLocal<SqlSession> tl = new ThreadLocal<SqlSession>();

    // 由于配置信息只需要加载一次，故使用 static 静态代码块
    static {
        // 加载配置文件,创建SqlSessionFactory
        try {
            InputStream inputStream = Resources.getResourceAsStream("mybaits-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static SqlSession openSession() {

        SqlSession sqlSession = tl.get();

        // 若当前线程没有绑定 session
        if (sqlSession == null) {
            sqlSession = sqlSessionFactory.openSession();
            tl.set(sqlSession);
        }

        return sqlSession;
    }

    public static void commit() {
        SqlSession sqlSession = openSession();
        sqlSession.commit();
        closeSession();
    }

    public static void rollback() {
        SqlSession sqlSession = openSession();
        sqlSession.rollback();
        closeSession();
    }

    // 资源释放
    public static void closeSession() {
        SqlSession sqlSession = tl.get();
        sqlSession.close();
    }

    // mapper的获取
    public static <T> T getMapper(Class<T> mapper) {
        SqlSession sqlSession = openSession();
        return sqlSession.getMapper(mapper);
    }
}
