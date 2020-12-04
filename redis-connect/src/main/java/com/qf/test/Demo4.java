package com.qf.test;

import com.sun.org.apache.bcel.internal.generic.GOTO;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.exceptions.JedisAskDataException;

import java.sql.SQLOutput;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/4
 * jedis 连接池操作
 **/

public class Demo4 {

    // 简单创建连接池
    @Test
    public void pool() {
        // 1. 创建连接池
        JedisPool pool = new JedisPool("192.168.1.103", 6379);

        // 2. 通过连接池获取jedis对象
        Jedis jedis = pool.getResource();

        // 3. 操作
        String value = jedis.get("stringUser");
        System.out.println("user:" + value);

        // 4. 释放资源  将获取到的对象还给连接池
        jedis.close();
    }

    // 配置连接池
    @Test
    public void pool2() {
        // 1.创建连接池配置信息
        GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
        poolConfig.setMaxTotal(100);    // 连接池中最大的活跃数
        poolConfig.setMaxIdle(10);    // 连接池中最小的空闲数
        poolConfig.setMinIdle(5);    // 连接池中最小的空闲数
        poolConfig.setMaxWaitMillis(3000);    // 当连接池空了以后，多久没有获取到 Jedis 对象就超时

        // 2.创建连接池
        JedisPool pool = new JedisPool(poolConfig, "192.168.1.103", 6379, 3000, "xxxx");

        // 3. 通过连接池获取jedis对象
        Jedis jedis = pool.getResource();

        // 4. 操作
        String value = jedis.get("stringUser");
        System.out.println("user:" + value);

        // 5. 释放资源  将获取到的对象还给连接池
        jedis.close();
    }

    // Redis管道操作

    /**
     * 如果一次性执行很多个命令，则可以考虑使用管道
     * 一次性将命令发送到redis服务器
     */
    @Test
    public void pipeline() {
        // 1.创建连接池
        JedisPool pool = new JedisPool( "192.168.1.103", 6379);

        // 2.获取连接对象
        Jedis jedis = pool.getResource();

        // 3.创建管道
        Pipeline pipeline = jedis.pipelined();

        // 4.执行1000次incr
        for (int i = 0; i<1000; i++)
            pipeline.incr("qq");
        pipeline.syncAndReturnAll();

        // 5.释放资源
        jedis.close();
    }

}
