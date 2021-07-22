package com.xty.mysh.service.impl;

import com.xty.mysh.service.LoginService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/21
 **/
public class LoinServiceImpl implements LoginService {
    @Override
    public boolean login(UsernamePasswordToken token) {
        Subject subject = SecurityUtils.getSubject();
        try {
            subject.login(token);
            return true;
        } catch (AuthenticationException e) {
            e.printStackTrace();
        }
        return subject.isAuthenticated();
    }

    @Override
    public void logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
    }
}
