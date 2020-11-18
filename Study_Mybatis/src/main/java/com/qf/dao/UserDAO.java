package com.qf.dao;

import com.qf.entity.User;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/7/11
 **/
public interface UserDAO {
    User queryUserById(Integer id);
}
