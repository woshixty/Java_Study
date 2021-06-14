package com.xty.demo.JWTTest;

import com.xty.demo.entity.User;
import com.xty.demo.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/11
 **/
@Service
public class UserServiceImpl implements UserService {

    UserMapper userMapper;

    @Autowired
    public UserServiceImpl( @Qualifier(value = "userMapper") UserMapper userMapper){
        this.userMapper = userMapper;
    }

    @Override
    public User login(long id, String passwd) {
        User userDB = userMapper.selectById(id);
        if (userDB!=null) {
            if (userDB.getPasswd().equals(passwd)) {
                return userDB;
            }
            throw new RuntimeException("用户密码错误");
        }
        throw new RuntimeException("用户不存在");
    }

}
