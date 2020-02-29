package org.example.rabbitmq.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 14:49
 * @version:
 */
public class simpleSend {

    public static final String QUEUE_NAME = "test_simple_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "Hello RabbitMQ222";
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        System.out.println("msg:" + msg);
        channel.close();
        connection.close();
    }
}
