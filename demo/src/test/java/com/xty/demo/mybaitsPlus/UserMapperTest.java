package com.xty.demo.mybaitsPlus;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/12
 **/

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserMapperTest {
    @Autowired
    UserMappers userMapper;

    @Test
    public void select() {
        List<User> userList = userMapper.selectList(null);
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
        int rows = userMapper.insert(user);
        System.out.println(rows);
    }

    @Test
    //通过主键列表查询
    public void selectIds() {
        List<Long> idList = Arrays.asList(1087982257332887553L, 1088248166370832385L);
        List<User> userList = userMapper.selectBatchIds(idList);
        userList.forEach(System.out::println);
    }

    @Test
    //通过map查询数据库
    public void selectByMap() {

    }
}