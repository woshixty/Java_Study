package com.baizhi;

import com.baizhi.work.Work;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

@SpringBootTest
@RunWith(SpringRunner.class)
public class TestRabbitMQ {

    // 注入 RabbitTemplate
    @Autowired
    private RabbitTemplate rabbitTemplate;

    // route 模型 路由模式
    @Test
    public void testRoute() {
        rabbitTemplate.convertAndSend("directs", "info", "发送 info 的 key 的路由信息");
    }

    // fanout 模型 广播
    public void testFanout() {
        rabbitTemplate.convertAndSend("logs", "", "fanout模型发送的消息");
    }

    // work 模型 发送string
    @Test
    public void testWork1() {
        rabbitTemplate.convertAndSend("work", "work模型");
    }

    // work 模型 发送work对象
    @Test
    public void testWork2() {
        Work work = new Work("Hello Work");
        // 不能这样发，因为对象不能转成 byte 字节
        rabbitTemplate.convertAndSend("work", work.getWork());
    }

    // HelloWorld 模型
    @Test
    public void test() {
        rabbitTemplate.convertAndSend("hello", "hello world");
    }

}
