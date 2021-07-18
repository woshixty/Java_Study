package com.qf.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/24
 **/

public class test {
    @Test
    public void xb () {
        Jedis jedis = new Jedis("8.131.57.6", 6379, 3000);
        jedis.auth("Flzx3qcYsyhl9t");

        jedis.set("name", "zhangsan");
        jedis.close();
    }
}
