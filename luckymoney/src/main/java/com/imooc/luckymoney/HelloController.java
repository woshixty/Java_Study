package com.imooc.luckymoney;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/7/16
 **/
@RestController
public class HelloController {

    @Value("${minMoney}")
    private BigDecimal minMoney;

    @Value("${description}")
    private String des;

    @GetMapping("/hello")
    public String say() {
        return "hello spring" + des;
    }
}
