package org.example.rabbitmq.queue.confirm;

import com.rabbitmq.client.*;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 21:40
 * @version:
 */
public class qrRecv {

    //public static final String QUEUE_NAME = "test_queue_confirm1";
    //public static final String QUEUE_NAME = "test_queue_confirm2";
    public static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicConsume(QUEUE_NAME, true, new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("recv[confirm]:"+new String(body, "utf-8"));
            }
        });
    }
}
