package org.example.service;

import java.util.List;
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
     * 查找用户名和密码
     * @param loginName
     * @return
     */
    Map<String, String> findPasswordByLoginName2(String loginName);

    /**
     * 查询角色
     * @param loginName
     * @return
     */
    List<String> findRoleByLoginName(String loginName);

    /**
     * 查询资源
     * @param loginName
     * @return
     */
    List<String> findPermissionByLoginName(String loginName);
}
