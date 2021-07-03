package com.xty.demo.config;

import com.xty.demo.interceptors.JWTinterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/12
 **/

@Configuration
public class InterceptorConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JWTinterceptor())
                .addPathPatterns("/jwt/verify")    //其他接口保护
                .excludePathPatterns("/jwt/login");    //用户登录放行
    }
}
