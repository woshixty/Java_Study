package com.xty.demo.JWTTest;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.SignatureGenerationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.xty.demo.entity.User;
import com.xty.demo.utils.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2020/10/11
 **/

@RestController
@RequestMapping("/jwt")
@Slf4j
public class JWTController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public Map<String, Object> login(long id, String passwd) {
        log.info("用户id: [[]]", id);
        log.info("密码: ", passwd);
        Map<String, Object> map = new HashMap<>();

        try {
            User user = userService.login(id, passwd);
            Map<String, String> payload = new HashMap<>();
            payload.put("id", user.getId().toString());
            payload.put("name", user.getName());

            //生成token令牌
            String token = JWTUtils.getToken(payload);

            map.put("status", true);
            map.put("msg", "认证成功");
            map.put("token", token);
        } catch (Exception e) {
            map.put("status", false);
            map.put("msg", e.getMessage());
        }

        return map;
    }

    @PostMapping("/verify")
    public Map<String, Object> test(HttpServletRequest request) {

        /*
        //验证token不再需要我们，我们处理业务逻辑即可，交由拦截器来做

        Map<String, Object> map = new HashMap<>();
        log.info("当前token为[{}]", token);
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
        return map;
        */
        Map<String, Object> map = new HashMap<>();
        //处理自己的业务逻辑
        map.put("status", true);
        map.put("msg", "请求成功！！！");

        String token = request.getHeader("token");
        DecodedJWT verify = JWTUtils.verify(token);
        log.info("用户id: [{}]", verify.getClaim("id"));
        log.info("用户name: [{}]", verify.getClaim("name"));
        return map;
    }

}
