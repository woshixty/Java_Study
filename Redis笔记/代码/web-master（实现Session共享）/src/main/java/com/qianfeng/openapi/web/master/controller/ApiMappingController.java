package com.qianfeng.openapi.web.master.controller;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.ApiMapping;
import com.qianfeng.openapi.web.master.bean.TableData;
import com.qianfeng.openapi.web.master.service.ApiMappingService;
import com.qianfeng.openapi.web.master.bean.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 路由管理
 */
@RestController
@RequestMapping("/sys/api_mapping")
public class ApiMappingController {

    @Autowired
    private ApiMappingService apiMappingService;

    @RequestMapping( "/table")
    public TableData table(ApiMapping apiMapping, Integer page, Integer limit) {
        PageInfo<ApiMapping> pageInfo = apiMappingService.getMappingList(apiMapping, page, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping( "/add")
    public AjaxMessage add(ApiMapping apiMapping) {
        try {

            apiMappingService.addApiMapping(apiMapping);
            return new AjaxMessage(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "添加失败");
    }

    @RequestMapping( "/update")
    public AjaxMessage update(ApiMapping apiMapping) {
        try {
            apiMappingService.updateApiMapping(apiMapping);
            return new AjaxMessage(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "修改失败");
    }

    @RequestMapping( "/info")
    public ApiMapping info(Integer id) {
        return apiMappingService.getMappingById(id);
    }

    @RequestMapping( "/del")
    public AjaxMessage delete(int[] ids) {
        try {
            apiMappingService.deleteMapping(ids);
            return new AjaxMessage(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "删除失败");
    }
}
