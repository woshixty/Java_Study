package com.xty.demo.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.util.Calendar;
import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/11
 **/
public class JWTUtils {
    private static String SIGN="hello_world";

    /*
    * 生成Token
    * @param map   传入payload
    * @return   返回token
    */
    public static String getToken(Map<String, String> map) {
        Calendar instance = Calendar.getInstance();
        instance.add(Calendar.DATE, 7);    //默认7天过期

        //创建JWT builder
        JWTCreator.Builder builder = JWT.create();

        //在payload 中添加数据
        map.forEach( (k, v) -> {
            builder.withClaim(k, v);
        });

        String token = builder.withExpiresAt(instance.getTime())  //指定令牌过期时间
                .sign(Algorithm.HMAC256(SIGN));  //sign

        return token;
    }

    /*
     * 验证Token
     * @param token
     * @return
     */
    public static DecodedJWT verify(String token) {
        return JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);  //一旦有错误，便会爆出异常
    }


    /*
     * 其实验证与返回 DecodedJWT 可以用一个函数
     * 获取Token中的信息
     * @param token
     * @return
    public static DecodedJWT getToken(String token) {
        DecodedJWT verify = JWT.require(Algorithm.HMAC256(SIGN)).build().verify(token);
        return verify;
    }
    */
}
