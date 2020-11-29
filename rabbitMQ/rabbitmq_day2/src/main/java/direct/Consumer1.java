package direct;

import com.rabbitmq.client.*;
import utils.RabbitMQUtils;

import java.io.IOException;


/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

public class Consumer1 {

    public static void main(String[] args) throws IOException {
        Connection connection = RabbitMQUtils.getConnection();
        Channel channel = connection.createChannel();

        //可以声明一个 exchangeName 日后只需修改这个变量即可
        String exchangeName = "logs_direct";

        //通道声明交换机以及交换的类型
        channel.exchangeDeclare(exchangeName, "direct");

        //创建一个临时队列
        String queue = channel.queueDeclare().getQueue();

        //临时队列和交换机的绑定
        channel.queueBind(queue, exchangeName, "info");
        channel.queueBind(queue, exchangeName, "error");
        channel.queueBind(queue, exchangeName, "warning");

        //获取消费的消息
        channel.basicConsume(queue, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) {
                System.out.println( "消费者1：" + new String( body ) );
            }
        } );
    }

}
