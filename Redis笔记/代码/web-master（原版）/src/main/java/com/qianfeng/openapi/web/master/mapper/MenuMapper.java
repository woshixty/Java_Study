package com.qianfeng.openapi.web.master.mapper;


import com.qianfeng.openapi.web.master.pojo.Menu;

import java.util.List;

public interface MenuMapper {

    List<Menu> getAllMenu();

    void deleteMenu(Integer id);

    void updateParentId(Integer id);

    void addMenu(Menu menu);

    Menu getMenuById(Integer id);

    void updateMenu(Menu menu);

    List<Menu> getUserMenu(Integer userId);

    List<Integer> getMenuRoleId(Integer menuId);

}
