package org.example.rabbitmq.queue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 15:32
 * @version:
 */
public class workSend {

    public static final String QUEUE_NAME = "test_work_queue";
    
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        for (int i = 0; i < 50; i++) {
            String msg = "Hello" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            Thread.sleep(200);
        }
        channel.close();
        connection.close();
    }
}
