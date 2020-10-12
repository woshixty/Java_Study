package com.xty.demo.mybaitsPlus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/12
 **/

@SpringBootApplication
@MapperScan("com.xty.demo.mybaitsPlus")
public class MPApplication {
    public static void main(String[] args){
        SpringApplication.run(MPApplication.class, args);
    }
}
