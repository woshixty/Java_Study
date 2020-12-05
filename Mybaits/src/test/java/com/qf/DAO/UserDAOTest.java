package com.qf.DAO;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageInterceptor;
import com.qf.entity.Hello;
import com.qf.util.MybaitsUtil;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/5
 **/
class UserDAOTest {

    @Test
    void selectAll() {
        UserDAO userDAO = MybaitsUtil.getMapper(UserDAO.class);
        List<Hello> hellos = userDAO.selectAll();
        hellos.forEach(System.out::println);
    }

    @Test
    void pageHelperTest() {
        // 在查询前 设置分页 查询第一页 每页两条数据
        // PageHelper 对其之后的第一个查询 进行分页功能追加
        /**
         * @pageNum 查询第几页的数据
         * @pageSize 每页有几条数据
         */
        PageHelper.startPage(2, 3);
        UserDAO userDAO = MybaitsUtil.getMapper(UserDAO.class);
        List<Hello> hellos = userDAO.selectAll();
        hellos.forEach(System.out::println);

        // 将查询结果封装到 PageInfo 对象中去
        // 可以在这里打一个断点 康康 pageInfo 里有些啥
        PageInfo<Hello> pageInfo = new PageInfo<>(hellos);

        /**
         * 分页插件注意事项
         * 不支持嵌套查询
         * 只有第一个方法会进行分页
         * 不支持带有 for update 查询的子句
         */
    }

}