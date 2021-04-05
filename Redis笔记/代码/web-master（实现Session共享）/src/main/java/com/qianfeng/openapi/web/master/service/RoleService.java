package com.qianfeng.openapi.web.master.service;


import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.Role;

import java.util.List;

public interface RoleService {
    PageInfo<Role> getRoleList(Role role, Integer page, Integer pageSize);

    List<Role> getRoleList(Role role);

    void addRoleMenu(Integer roleId, Integer[] menuIds);

    List<Integer> getRoleMenuIds(Integer roleId);

    void addRole(Role role);

    void updateRole(Role role);

    void deleteRole(int[] ids);

    Role getRoleId(Integer id);
}
