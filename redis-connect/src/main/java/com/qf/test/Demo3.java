package com.qf.test;

import com.alibaba.fastjson.JSON;
import com.qf.entity.User;
import org.junit.Test;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * redis string的形式存储对象
 **/
public class Demo3 {
    // 存储对象 - 以 string 形式存储在 redis 中
    @Test
    public void setByteArray() {
        // 1.连接 redis 服务
        Jedis jedis = new Jedis("192.168.1.103", 6379);

        // 2.1 准备key (String) - value (User)
        String key = "stringUser";
        User value = new User(2, "李四", new Date());

        // 2.2 将 user对象 序列化为 json 字符串
        // 使用 fastJSON 工具，导入依赖
        String stringValue = JSON.toJSONString(value);

        // 2.3 将 key 和 value 存储到 Redis
        jedis.set(key, stringValue);

        // 3.释放资源
        jedis.close();
    }

    // 获取对象 - 以 string 形式在 redis 中获取
    @Test
    public void getByteArray() {
        // 1.连接 redis 服务
        Jedis jedis = new Jedis("192.168.1.106", 6379);

        // 2.1 准备key
        String Key = "stringUser";

        // 2.2 去查询
        String value = jedis.get(Key);

        // 2.3 反序列化
        User user = JSON.parseObject(value, User.class);

        // 打印下 user 对象
        System.out.println(user);

        // 3.释放资源
        jedis.close();
    }
}
