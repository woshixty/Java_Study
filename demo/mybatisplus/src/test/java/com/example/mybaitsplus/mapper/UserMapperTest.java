package com.example.mybaitsplus.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.example.mybaitsplus.dao.UserMapper;
import com.example.mybaitsplus.entity.User;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.*;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/14
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {

    @Autowired
    UserMapper userMappers;

    @Test
    public void select() {
        List<User> userList = userMappers.selectList(null);
        Assert.assertEquals(5, userList.size());
        userList.forEach(System.out::println);
    }

    @Test
    public void insert() {
        User user = new User();
        user.setName("刘明强");
        user.setAge(3);
        user.setManagerId(1088248166370832385L);
        user.setCreateTime(LocalDateTime.now());
        int rows = userMappers.insert(user);
        System.out.println(rows);
    }

    @Test
    //通过主键列表查询
    public void selectIds() {
        List<Long> idList = Arrays.asList(1087982257332887553L, 1088248166370832385L);
        List<User> userList = userMappers.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }

    @Test
    //通过map查询数据库
    public void selectByMap() {
        Map<String, Object> columnMap = new HashMap<>();
        columnMap.put("manager_id", 1088248166370832385L);    // 记得要用数据库中的列名 切记！切记！切记！！！
//        columnMap.put("managerId", 1094592041087729666L);
        List<User> userList = userMappers.selectByMap(columnMap);
        userList.forEach(System.out::println);
    }

    @Test
    /**
     * wrapper的使用
     * like  likeRight  likeLeft -- 是否包含
     * lt--小于  ge--大于
     * between--查找在某个区间的
     * isNotMUll--某个字段不能为空
     * orderByDesc--降序排列
     *
    * */
    public void selectByWrapper() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name", "雨").lt("age", 40);
        // 使用wrapper查找数据库 条件构造器
        // 名字包含'雨'且年龄小于40
        List<User> userList = userMappers.selectList(queryWrapper);
        userList.forEach(System.out::println);

        /**
         * 需求：
         * 名字为王姓，并且(年龄小于40或者邮箱不为空)
         * 这儿我依然使用了上面的 queryWrapper 会导致条件叠加，所以下面的这个必须重新 new 一个 QueryWrapper
         * */
        QueryWrapper<User> wrapper = new QueryWrapper<>();

        wrapper.likeRight("name", "王").and(wq -> wq.lt("age", 40).or().isNotNull("email"));
        userList = userMappers.selectList(wrapper);
        userList.forEach(System.out::println);

    }

    /**
     * 某个字段为多个值, 例如 查找年龄为 31 32 34 35 的学生
     * in("age", Arrays.asList(31, 32, 34, 35))
     *
     * 只返回满足条件的其中一条语句即可
     * last    (!!! 慎用，无视优化规则，直接拼接到sql的最后，谨慎使用) (!!! 只能使用一次，多次调用以后以最后一次为准)
     */

    @Test
    public void lastTest() {
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("age", Arrays.asList(31,32,34,35)).last("limit 1");

        List<User> userList = userMappers.selectList(queryWrapper);
        userList.forEach(System.out::println);

//        queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") &&
//                !info.getColumn().equals("manager_id"));

        String name = "hello",email="hello";
        queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
                .like(StringUtils.isNotBlank(name), "email", email);
    }

    /**
     * select 默认会查询所有字段
     *
     * 例如 queryWrapper.like("name", "雨").lt("age", 40); 的 sql 语句为
     * SELECT id,name,age,email,manager_id,create_time FROM user WHERE (name LIKE ? AND age < ?)
     *
     * 如果只需要查询部分字段，则可以使用
     * queryWrapper.select("name", "id").like("name", "雨").lt("age", 40);
     *
     * select 在前面与后面都可以
     *
     * 如果字段特别多，则可以排除一些字段
     * 例如 queryWrapper.select(User.class, info -> !info.getColumn().equals("create_time") &&
     *                 !info.getColumn().equals("manager_id"));
     *
     * condition
     * 与 like 相关
     * 例如  queryWrapper.like(StringUtils.isNotBlank(name), "name", name)
     *                 .like(StringUtils.isNotBlank(name), "email", email);
     */

    @Test
    public void lambda() {
        /**
         * 构造 lambda 条件构造器的三种方式
         * lambda 条件构造器可以防误写
         */

        // 第一种方式
        LambdaQueryWrapper<User> lambda1 = new QueryWrapper<User>().lambda();

        //第二种方式
        LambdaQueryWrapper<User> lambda2 = new LambdaQueryWrapper<User>();

        //第三种方式
        LambdaQueryWrapper<User> lambda3 = Wrappers.<User>lambdaQuery();

        lambda3.like(User::getName, "雨").lt(User::getAge, 40);    //where name like '%雨%'
        List<User> list = userMappers.selectList(lambda3);
        list.forEach(System.out::println);

        //以下使用自己写的 selectAll 方法
        List<User> users = userMappers.selectAll(lambda3);
        list.forEach(System.out::println);
//        System.out.println(user);
    }



}