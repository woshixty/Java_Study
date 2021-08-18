package com.example.xty.controller;

import com.example.xty.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import javax.servlet.http.HttpSession;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/17
 **/
@Controller
public class IndexController {

    /**
     * 来登录页
     * @return
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String login(User user, HttpSession session, Model model) {
        if (user.getUserName() != null && user.getPassword().equals("123456")) {
            session.setAttribute("loginUser", user);
            //登录·成功重定向到index页面
            return "redirect:/index.html";
        } else {
            model.addAttribute("msg", "账号或者密码错误");
            return "login";
        }
    }

    @GetMapping("/index.html")
    public String indexPage(HttpSession session, Model model) {
        //检验是否登录
        Object loginUser = session.getAttribute("loginUser");
        if (loginUser != null) {
            return "index";
        } else {
            model.addAttribute("msg", "请先登录在访问");
            return "login";
        }
    }
}
