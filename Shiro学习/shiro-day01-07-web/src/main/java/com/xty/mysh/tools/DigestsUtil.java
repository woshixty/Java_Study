package com.xty.mysh.tools;

import org.apache.shiro.crypto.SecureRandomNumberGenerator;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/19
 * 生成摘要，散列算法
 **/
public class DigestsUtil {
    //加密方式
    public static final String SHA1="SHA-1";
    //加密次数
    public static final Integer ITERATION=512;
    /**
     * SHA-1摘要算法
     * @param input
     * @param salt
     * @return
     */
    public static String shal(String input, String salt) {
        return new SimpleHash(SHA1, input, salt, ITERATION).toString();
    }

    /**
     * 随机生成salt
     * @return
     */
    public static String generateSalt() {
        SecureRandomNumberGenerator randomNumberGenerator = new SecureRandomNumberGenerator();
        return randomNumberGenerator.nextBytes().toHex();
    }

    /**
     * 生成密码和salt的秘文
     * @param passwordPlan
     * @return
     */
    public static Map<String, String> entryptPassword(String passwordPlan) {
        Map<String, String> map = new HashMap<>();
        String salt = generateSalt();
        String password = shal(passwordPlan, salt);
        map.put("salt", salt);
        map.put("password", password);
        return map;
    }
}
