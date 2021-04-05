package com.qianfeng.openapi.web.master.service;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.qianfeng.openapi.web.master.pojo.Menu;

import java.util.List;

public interface MenuService {
    List<Menu> getMenuTree();

    List<Menu> getMenuList();

    List<Menu> getFullMenuTree();

    void deleteMenus(Integer[] ids);

    void addMenu(Menu menu);

    Menu getMenuById(Integer id);

    void updateMenu(Menu menu);

    List<Menu> getUserPermission(Integer userId);

    List<Menu> getUserMenuList(Integer userId);

    /**
     * 将用户的权限信息存储到Redis
     * @param key
     */
    void setUserMenuList(String key) throws JsonProcessingException;
}
