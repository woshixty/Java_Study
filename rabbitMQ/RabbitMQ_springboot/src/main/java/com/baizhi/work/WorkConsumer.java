package com.baizhi.work;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

@Component
//@RabbitListener  该注解可以直接加在方法上
public class WorkConsumer {

    //一个消费者
    @RabbitListener( queuesToDeclare = @Queue("work"))
    public void receive_one(String message) {
        System.out.println("message1:" + message);
    }

    //二个消费者
    @RabbitListener( queuesToDeclare = @Queue("work"))
    public void receive_two(Work work) {
        System.out.println("work:" + work.getWork());
    }
}
