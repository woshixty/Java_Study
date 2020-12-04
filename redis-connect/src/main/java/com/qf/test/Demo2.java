package com.qf.test;

import com.qf.entity.User;
import org.junit.Test;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;

import java.util.Date;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/12/3
 * redis 字节数组的形式存储对象
 **/

public class Demo2 {

    // 存储对象 - 以 byte[] 形式存储在 redis 中
    @Test
    public void setByteArray() {
        // 1.连接 redis 服务
        Jedis jedis = new Jedis("192.168.1.103", 6379, 3000);
        jedis.auth("xxxx");

        // 2.1 准备key (String) - value (User)
        String key = "user";
        User value = new User(1, "张三", new Date());

        // 2.2 将 key 和 value 转换为 byte[]
        // 必须要让 user 对象实现 序列化接口 Serializable
        // 导入 spring-context 依赖，使用工具类转化
        // SerializationUtils.deserialize();  反序列化
        // SerializationUtils.serialize();  序列化
        byte[] byteKey = SerializationUtils.serialize(key);
        byte[] byteValue = SerializationUtils.serialize(value);

        // 2.3 将 key 和 value 存储到 Redis
        jedis.set(byteKey, byteValue);

        // 3.释放资源
        jedis.close();
    }

    // 获取对象 - 以 byte[] 形式在 redis 中获取
    @Test
    public void getByteArray() {
        // 1.连接 redis 服务
        Jedis jedis = new Jedis("192.168.1.106", 6379);

        // 2.1 准备key
        String Key = "user";

        // 2.2 将 key 和 value 转换为 byte[]
        byte[] byteKey = SerializationUtils.serialize(Key);

        // 2.3 从 Redis 获取
        byte[] byteValue = jedis.get(byteKey);

        // 2.4 反序列化
        User user = (User) SerializationUtils.deserialize(byteValue);

        // 打印下 user 对象
        System.out.println(user);

        // 3.释放资源
        jedis.close();
    }
}
