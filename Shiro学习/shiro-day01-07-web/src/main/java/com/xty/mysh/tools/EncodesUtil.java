package com.xty.mysh.tools;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.codec.Hex;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/19
 * 编码工具类
 **/
public class EncodesUtil {
    /**
     * HEX-String-byte[]
     * @param input
     * @return
     */
    public static String encodeHex(byte[] input) {
        return Hex.encodeToString(input);
    }

    /**
     * HEX-byte[]-String
     * @param input
     * @return
     */
    public static byte[] decodeHex(String input) {
        return Hex.decode(input);
    }

    /**
     * Base64-byte[]-String
     * @param input
     * @return
     */
    public static String encodeBase64(byte[] input) {
        return Base64.encodeToString(input);
    }

    /**
     * Base64-byte[]-String
     * @param input
     * @return
     */
    public static byte[] decodeBase64(String input) {
        return Base64.decode(input);
    }
}
