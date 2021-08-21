package com.example.xty.controller;

import com.example.xty.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Arrays;
import java.util.List;

/**
 * @author qyyzxty@icloud.com
 * @data 2021/8/21
 **/
@Controller
public class TableController {
    @GetMapping("/basic_table")
    public String basic_table() {
        return "table/basic_table";
    }

    @GetMapping("/dynamic_table")
    public String dynamic_table(Model model) {
        //表格内容的动态遍历
        List<User> users = Arrays.asList(new User("张三", "123456"), new User("李四", "123456"),
                new User("王五", "123456"));
        model.addAttribute("users", users);
        return "table/dynamic_table";
    }

    @GetMapping("/editable_table")
    public String editable_table() {
        return "table/editable_table";
    }

    @GetMapping("/pricing_table")
    public String pricing_table() {
        return "table/pricing_table";
    }

    @GetMapping("/responsive_table")
    public String responsive_table() {
        return "table/responsive_table";
    }

}
