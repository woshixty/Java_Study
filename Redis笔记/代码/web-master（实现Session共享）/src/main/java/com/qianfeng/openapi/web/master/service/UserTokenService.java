package com.qianfeng.openapi.web.master.service;

import com.github.pagehelper.PageInfo;
import com.qianfeng.openapi.web.master.pojo.UserToken;

public interface UserTokenService {
    PageInfo<UserToken> getTokenList(UserToken criteria, int page, int pageSize);

    UserToken getTokenById(int id);

    void updateToken(UserToken token);

    void addToken(UserToken token);

    void deleteUserToken(int[] ids);
}
