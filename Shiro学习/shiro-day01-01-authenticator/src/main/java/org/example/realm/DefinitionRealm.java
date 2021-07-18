package org.example.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.example.service.SecurityService;
import org.example.service.impl.SecurityServiceImpl;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 **/
public class DefinitionRealm extends AuthorizingRealm {
    /**
     * 鉴权方法
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 认证方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取登录名
        String loginName = (String) token.getPrincipal();
        SecurityService securityService = new SecurityServiceImpl();
        String password = securityService.findPasswordByLogin(loginName);
        if ("".equals(password)) {
            throw new UnknownAccountException("账户不存在");
        }
        return new SimpleAuthenticationInfo(loginName, password, getName());
    }
}
