package com.xty.demo;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xty.demo.entity.User;
import com.xty.demo.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.StringTokenizer;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoApplicationTests {

    @Resource
    private UserMapper userMapper;

    @Test
    public void select() {
        List<User> users = userMapper.selectList(null);
        users.forEach(System.out::println);
    }

    @Test
    //令牌的获取
    void  contextLoads() {
        HashMap<String, Object> map = new HashMap<>();

        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.HOUR, 2);

        String token = JWT.create()
                .withHeader(map)    //header
                .withClaim("userId", 23)    //payload
                .withClaim("username", "hello_world")    //payload
                .withExpiresAt(instance.getTime())    //制定令牌过期时间
                .sign(Algorithm.HMAC256("hello_world"));

        System.out.println(token);
    }

    @Test
    public void test() {
        //创建验证对象
        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256("hello_world")).build();

        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJleHAiOjE2MDIzODkyMjEsInVzZXJJZCI6MjMsInVzZXJuYW1lIjoiaGVsbG9fd29ybGQifQ.UOKOVXyk_kGnj_UnZBonWwDNDVVyTPYN9OhpTMMooLw";
        DecodedJWT verify = jwtVerifier.verify(token);

        System.out.println(verify.getClaim("userId").asInt());
        System.out.println(verify.getClaim("username").asString());
    }

}
