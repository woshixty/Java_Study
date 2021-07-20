package org.example.service.impl;

import org.example.service.SecurityService;
import org.example.tools.DigestsUtil;

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
}
