package direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import utils.RabbitMQUtils;

import java.io.IOException;


/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/29
 **/

public class Provider {

    public static void main(String[] args) throws IOException {
        //获取连接对象
        Connection connection = RabbitMQUtils.getConnection();

        //获取连接中的管道的对象
        Channel channel = connection.createChannel();

        //通过通道声明交换机
        /**
         * @exchange  交换机名称
         * @type  路由模式 有 topic 和 direct
         */
        channel.exchangeDeclare("logs_direct", "direct");

        //发送消息
        String routingKey = "info";
        channel.basicPublish("logs_direct", routingKey, null, ("这是 direct 模型发布的基于 route key：[" + "]发送的消息").getBytes() );

        //关闭资源
        RabbitMQUtils.closeConnection(channel, connection);
    }

}
