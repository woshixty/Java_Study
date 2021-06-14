package com.xty.demo.JWTTest;

import com.xty.demo.entity.User;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/11
 **/
public interface UserService {
    User login(long id, String passwd);  //登录接口
}
