package helloworld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.junit.Test;

import java.io.IOException;
import java.nio.channels.Channels;
import java.util.concurrent.TimeoutException;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/27
 **/

public class Send {

    //生产消息
    @Test
    public void testSendMessage() throws IOException, TimeoutException {

        //创建连接mq的连接工厂对象
        ConnectionFactory factory = new ConnectionFactory();

        //设置连接rabbitMQ的主机
        factory.setHost("39.101.137.255");

        //设置端口号
        factory.setPort(5672);

        //设置连接的虚拟主机
        factory.setVirtualHost("/ems");

        //设置访问虚拟主机的 用户名 和 密码
        factory.setUsername("ems");
        factory.setPassword("123");

        //获取连接的对象
        Connection connection = factory.newConnection();

        //获取连接中的管道
        Channel channel = connection.createChannel();

        /**
         * @queue 队列名称，若队列不存在则自动创建
         * @durable 用来定义队列是否需要持久化，即 重启 之后队列是否删除
         * @axclusive 是否独占队列
         * @autoDelete 是否在消费完成后自动删除队列
         * @arguments 额外附加参数
         */
        channel.queueDeclare("hello", false, false, false, null );

        //发布消息
        /**
         * @exchange 交换机名称
         * @routingKey 队列名称
         * @props 传递消息额外设置  如 MessageProperties.PERSISTENT_TEXT_PLAIN 则是让消息持久化的设置
         * @最后一个参数 消息具体内容
         */
        channel.basicPublish("", "hello", MessageProperties.PERSISTENT_TEXT_PLAIN, "hello rabbitMQ".getBytes());

        channel.close();
        connection.close();
    }
}
