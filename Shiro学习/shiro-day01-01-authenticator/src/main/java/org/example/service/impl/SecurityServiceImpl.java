package org.example.service.impl;

import org.example.service.SecurityService;
import org.example.tools.DigestsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 **/
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String findPasswordByLoginName(String loginName) {
        return "123";
    }

    @Override
    public Map<String, String> findPasswordByLoginName2(String loginName) {
        return DigestsUtil.entryptPassword("123");
    }

    @Override
    public List<String> findRoleByLoginName(String loginName) {
        List<String> list = new ArrayList<>();
        list.add("admin");
        list.add("dev");
        return list;
    }

    @Override
    public List<String> findPermissionByLoginName(String loginName) {
        List<String> list = new ArrayList<>();
        list.add("order:add");
        list.add("order:list");
        list.add("order:del");
        return list;
    }
}
