package com.qf.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qf.dao.UserDAO;
import com.qf.entity.Page;
import com.qf.entity.User;
import com.qf.service.UserService;
import com.qf.util.MyBatisUtil;

import java.util.List;

public class UserServiceImpl implements UserService {
    @Override
    public PageInfo<User> queryUsers(Page page) {
        UserDAO mapper = MyBatisUtil.getMapper(UserDAO.class);
        // 分页设置
        PageHelper.startPage(page.getPageNum(),page.getPageSize());
        List<User> users = mapper.queryUsers();
        // 封装PageInfo
        return new PageInfo<User>(users);
    }
}
