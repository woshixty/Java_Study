package com.xty.mysh.web;

import com.xty.mysh.service.LoginService;
import com.xty.mysh.service.impl.LoinServiceImpl;
import org.apache.shiro.authc.UsernamePasswordToken;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/21
 **/
@WebServlet(urlPatterns = "/login")
public class LoginServlet extends HttpServlet {
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得用户名和密码
        String username = req.getParameter("loginName");
        String password = req.getParameter("password");
        //构建登录使用的token
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        //登录操作
        LoginService loginService = new LoinServiceImpl();
        boolean isLogin = loginService.login(token);
        //如果登陆成功，跳转home.jsp
        if (isLogin) {
            req.getRequestDispatcher("/home").forward(req, resp);
        }
        //如果登录失败，跳转继续登录界面
        resp.sendRedirect("login.jsp");
    }
}
