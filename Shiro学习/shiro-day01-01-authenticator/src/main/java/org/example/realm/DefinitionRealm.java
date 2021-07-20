package org.example.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.example.service.SecurityService;
import org.example.service.impl.SecurityServiceImpl;
import org.example.tools.DigestsUtil;

import java.util.Map;

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

    public DefinitionRealm() {
        //指定密码匹配方式sha-1
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher(DigestsUtil.SHA1);
        //指定密码迭代次数
        hashedCredentialsMatcher.setHashIterations(DigestsUtil.ITERATION);
        //使用父层方式使匹配方式生效
        setCredentialsMatcher(hashedCredentialsMatcher);
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
//        String password = securityService.findPasswordByLoginName(loginName);
        Map<String, String> map = securityService.findPasswordByLoginName2(loginName);
        if ("".equals(map.isEmpty())) {
            throw new UnknownAccountException("账户不存在");
        }
        String salt = map.get("salt");
        String password = map.get("password");
        return new SimpleAuthenticationInfo(loginName, password, ByteSource.Util.bytes(salt), getName());
    }
}
