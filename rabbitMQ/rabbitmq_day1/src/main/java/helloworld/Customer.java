package helloworld;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/27
 **/

public class Customer {

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost("39.101.137.255");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/ems");
        connectionFactory.setUsername("ems");
        connectionFactory.setPassword("123");

        //创建连接对象
        Connection connection = connectionFactory.newConnection();

        //创建通道
        final Channel channel = connection.createChannel();


        channel.basicQos(1);  //每次只能消费一个消息


        //通道绑定对象
        channel.queueDeclare("hello", false, false, false, null);

        //消费消息
        /**
         * @queue 消费的那个队列名称
         * @autoAck 开启消息的自动确认机制  false 表示不会自动确认消息 若消息被消费了 但是未被确认 则依然会在队列中存在
         * @最后一个参数 消费时的回调接口
         */
        channel.basicConsume("hello", true, new DefaultConsumer(channel) {
            @Override    // 最后一个参数 body 是我们取出的消息
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("new string(body) =" + new String(body));

                //手动确认消息
                /**
                 * @第一个参数  确认队列中那个具体的参数
                 * @multiple  是否开启多个消息同时确认
                 */
                channel.basicAck(envelope.getDeliveryTag(), false);
            }
        } );

        // 如果不关闭则会一直监听我们的队列
        channel.close();
        connection.close();
    }

}
