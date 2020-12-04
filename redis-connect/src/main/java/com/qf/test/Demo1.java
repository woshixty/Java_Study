package com.qf.test;

import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/30
 * 连接 redis
 **/

public class Demo1 {

    @Test
    public void test() {
        // 1.连接redis
        Jedis jedis = new Jedis("192.168.1.116", 6379);

        // 2.操作redis -- 因为 redis 的命令是啥，Jedis 的方法就是啥
        jedis.set("name","李四");

        // 3.释放资源
        jedis.close();
    }
}
