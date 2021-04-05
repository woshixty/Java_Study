package com.qf.dao;

import com.qf.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDAO {

    List<User> queryUsers();
}
