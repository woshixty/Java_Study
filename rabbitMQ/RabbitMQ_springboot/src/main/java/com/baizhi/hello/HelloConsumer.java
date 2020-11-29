package com.baizhi.hello;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

@Component    // 默认值为 持久化 非独占 不是自动删除队列
@RabbitListener(queuesToDeclare = @Queue(value = "hello", durable = "false", autoDelete = "true"))    // @Queue 注解用于创建队列
public class HelloConsumer {

    @RabbitHandler
    public void receive(String message) {
        System.out.println("message：" + message);
    }

}