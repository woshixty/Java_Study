package com.xty.mysh.service;

import org.apache.shiro.authc.UsernamePasswordToken;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/21
 **/
public interface LoginService {
    /**
     * 登入操作
     * @param token
     * @return
     */
    boolean login(UsernamePasswordToken token);

    /**
     * 登出服务
     */
    void logout();
}
