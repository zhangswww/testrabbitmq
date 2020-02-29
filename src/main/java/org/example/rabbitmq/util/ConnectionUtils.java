package org.example.rabbitmq.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 14:39
 * @version:
 */
public class ConnectionUtils {

    /**
     * 获取mq链接
     */
    public static Connection getConnection() throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("127.0.0.1");
        factory.setPort(5672);
        factory.setVirtualHost("/vhost_zhang");
        factory.setUsername("zhang");
        factory.setPassword("zhang");
        return factory.newConnection();
    }
}
