package com.qianfeng.openapi.web.master.controller;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.bean.TableData;
import com.qianfeng.openapi.web.master.pojo.UserToken;
import com.qianfeng.openapi.web.master.service.UserTokenService;
import com.qianfeng.openapi.web.master.bean.AjaxMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * token管理
 *
 * @author menglili
 */
@RestController
@RequestMapping("/sys/token")
public class TokenController {

    @Autowired
    private UserTokenService userTokenService;


    @RequestMapping( "/table")
    public TableData table(UserToken token, Integer page, Integer limit) {
        PageInfo<UserToken> pageInfo = userTokenService.getTokenList(token, page, limit);
        return new TableData(pageInfo.getTotal(), pageInfo.getList());
    }

    @RequestMapping( "/info")
    public UserToken info(int id) {
        return userTokenService.getTokenById(id);
    }

    @RequestMapping( "/update")
    public AjaxMessage update(UserToken info) {
        try {
            userTokenService.updateToken(info);
            return new AjaxMessage(true, "修改成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "修改失败");
        }
    }

    @RequestMapping( "/add")
    public AjaxMessage add(UserToken info) {
        try {
            userTokenService.addToken(info);
            return new AjaxMessage(true, "添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return new AjaxMessage(false, "添加失败");
        }
    }

    @RequestMapping("/del")
    public AjaxMessage delete(int[] ids) {
        try {
            userTokenService.deleteUserToken(ids);
            return new AjaxMessage(true, "删除成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new AjaxMessage(false, "删除失败");
    }

}
