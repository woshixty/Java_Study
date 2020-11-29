package utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.sun.istack.internal.localization.NullLocalizable;

/**
 * @author qyyzxty@icloud.com
 * @data 2020/11/27
 **/
public class RabbitMQUtils {

    private static ConnectionFactory connectionFactory;
    private static String host = "39.101.137.255";
    private static Integer port = 5672;
    private static String virtualHost = "/ems";
    private static String userName = "ems";
    private static String password = "123";

    static {
        connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setVirtualHost(virtualHost);
        connectionFactory.setUsername(userName);
        connectionFactory.setPassword(password);
    }

    //定义连接对象的方法
    public static Connection getConnection () {
        try {

            // connectionFactory 是重量级资源，我们希望他在类加载的时候就创建出来，而且只创建一次，故放在静态代码块中
            // ConnectionFactory connectionFactory = new ConnectionFactory();

            // 而下面的信息也基本只会创建时就定下来，故也可以放在静态代码块中
//            connectionFactory.setHost(host);
//            connectionFactory.setPort(port);
//            connectionFactory.setVirtualHost(virtualHost);
//            connectionFactory.setUsername(userName);
//            connectionFactory.setPassword(password);
            //把 connection 对象返回
            return connectionFactory.newConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    //关闭通道和连接
    public static void closeConnection(Channel channel, Connection connection) {
        try {

            if (channel != null)
                channel.close();

            if (connection != null)
                connection.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
