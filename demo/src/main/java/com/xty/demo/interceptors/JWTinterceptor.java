package com.xty.demo.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.xty.demo.utils.JWTUtils;
import net.bytebuddy.dynamic.scaffold.MethodRegistry;
import org.apache.http.HeaderIterator;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/12
 **/
public class JWTinterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Map<String, Object> map = new HashMap<>();
        String token = request.getHeader("token");

        try {
            DecodedJWT verify = JWTUtils.verify(token);
            map.put("status", true);
            map.put("msg", "请求成功!");
        } catch (TokenExpiredException e) {
            e.printStackTrace();
            map.put("msg", "token已经过期!");
        } catch (SignatureGenerationException e) {
            e.printStackTrace();
            map.put("msg", "无效签名!");
        }catch (AlgorithmMismatchException e) {
            e.printStackTrace();
            map.put("msg", "token算法不一致");
        }catch (Exception e) {
            e.printStackTrace();
            map.put("msg", "token无效");
        }
        map.put("status", false);
        // 将map转为json   jackson
        String json = new ObjectMapper().writeValueAsString(map);
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().println(json);

        return false;
    }
}
