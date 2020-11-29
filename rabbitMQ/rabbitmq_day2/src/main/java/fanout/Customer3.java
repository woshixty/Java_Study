package fanout;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/28
 **/

public class Customer3 {

    public static void main(String[] args) throws IOException {

        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //通道绑定交换机
        channel.exchangeDeclare("logs", "fanout");

        //临时队列，获取临时队列名字
        String queueName = channel.queueDeclare().getQueue();

        //绑定交换机和队列
        channel.queueBind(queueName, "logs", "");

        //消费消息
        channel.basicConsume( queueName, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println("消费者3：" + new String(body) );

            }
        } );
    }

}
