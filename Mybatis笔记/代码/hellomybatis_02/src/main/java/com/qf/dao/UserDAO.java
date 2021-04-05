package com.qf.dao;

import com.qf.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface UserDAO {

    List<User> queryUsers();
    /*User queryUserById(@Param("id") Integer id);
    User queryUserByUsername(@Param("username") String username);*/
    User queryUser(User user);
    List<User>  queryUser2(User user);
    Integer deleteUser(@Param("id") Integer id);
    Integer updateUser(User user);
    Integer insertUser(User user);

    Integer deleteManyUser(List<Integer> ids);

    Integer insertManyUser(List<User> users);
}
