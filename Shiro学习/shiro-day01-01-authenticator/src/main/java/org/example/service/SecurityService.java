package org.example.service;

import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 **/
public interface SecurityService {
    /**
     * 查找用户名和密码
     * @param loginName
     * @return
     */
    String findPasswordByLoginName(String loginName);

    /**
     *
     * @param loginName
     * @return
     */
    Map<String, String> findPasswordByLoginName2(String loginName);
}
