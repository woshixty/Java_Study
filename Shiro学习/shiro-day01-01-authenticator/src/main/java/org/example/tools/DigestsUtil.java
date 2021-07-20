package org.example.tools;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/19
 * 生成摘要
 **/
public class DigestsUtil {
    //加密方式
    private static final String SHA1="SHA-1";
    //加密次数
    private static final Integer ITERATION=512;
    /**
     * SHA-1摘要算法
     * @param input
     * @param salt
     * @return
     */
    public static String shal(String input, String salt) {
        return new SimpleHash(SHA1, input, salt, ITERATION).toString();
    }
}
