package org.example.client;

import org.example.tools.EncodesUtil;
import org.junit.Test;

/**
 * @author qyyzxty@icloud.com
 * 2021/7/19
 * 测试编码和解码
 **/
public class ClientTest {
    @Test
    public void testHex() {
        String val = "Hello";
        String flag = EncodesUtil.encodeHex(val.getBytes());
        String valHandler = new String(EncodesUtil.decodeHex(flag));
        System.out.println("比较结果" + val.equals(valHandler));
        System.out.println("flag：" + flag);
    }
    @Test
    public void testBase64() {
        String val = "Hello";
        String flag = EncodesUtil.encodeBase64(val.getBytes());
        String valHandler = new String(EncodesUtil.decodeBase64(flag));
        System.out.println("比较结果" + val.equals(valHandler));
        System.out.println("flag：" + flag);
    }
}
