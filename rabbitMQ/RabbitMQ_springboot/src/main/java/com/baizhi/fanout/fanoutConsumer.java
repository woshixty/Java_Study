package com.baizhi.fanout;

import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

@Component
public class fanoutConsumer {

    @RabbitListener( bindings = {
            @QueueBinding(
                    value = @Queue,  //创建临时队列
                    exchange = @Exchange( value = "logs", type = "fanout")  //绑定交换机
            )
    } )
    public void receive(String message) {
        System.out.println("message:" + message);
    }

}
