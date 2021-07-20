package org.example;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;
import org.junit.Test;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/18
 * Shiro的第一个例子
 **/
public class HelloShiro {

    @Test
    public void testPermissionRealm() {
        Subject subject = shiroLogin2();
        //打印登录信息
        System.out.println("登录结果：" + subject.isAuthenticated());
        //检验当前用户是否有管理员的角色
        System.out.println("是否有管理员角色：" + subject.hasRole("admin"));
        //检验当前用户没有的角色
        try {
            subject.checkRole("coder");
            System.out.println("当前用户有coder角色");
        } catch (Exception e) {
            System.out.println("当前用户没有coder角色");
        }
        //校验当前用户的权限信息
        System.out.println("是否有查看订单的权限" + subject.isPermitted("order:list"));
        //检验当前用户满意的权限
        try {
            subject.checkPermission("order:update");
            System.out.println("当前用户有修改权限");
        } catch (Exception e) {
            System.out.println("当前用户没有修改权限");
        }
    }

    @Test
    public void shiroLogin() {
        //导入INI配置创建工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //工厂构建安全管理器
        SecurityManager securityManager = factory.getInstance();
        //使用工具生效安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //使用工具获得subject主体
        Subject subject = SecurityUtils.getSubject();
        //构建账户密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jay", "123");
        //使用subject主体去登陆
        subject.login(usernamePasswordToken);
        //打印登录信息
        System.out.println("登录结果" + subject.isAuthenticated());
    }

    public Subject shiroLogin2() {
        //导入INI配置创建工厂
        Factory<SecurityManager> factory = new IniSecurityManagerFactory("classpath:shiro.ini");
        //工厂构建安全管理器
        SecurityManager securityManager = factory.getInstance();
        //使用工具生效安全管理器
        SecurityUtils.setSecurityManager(securityManager);
        //使用工具获得subject主体
        Subject subject = SecurityUtils.getSubject();
        //构建账户密码
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("jay", "123");
        //使用subject主体去登陆
        subject.login(usernamePasswordToken);
        //打印登录信息
        System.out.println("登录结果" + subject.isAuthenticated());
        return subject;
    }
}
