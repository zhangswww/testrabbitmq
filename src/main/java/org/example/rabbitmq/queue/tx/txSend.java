package org.example.rabbitmq.queue.tx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:事务
 * @author:张士威
 * @date:2020/2/29 21:15
 * @version:
 */
public class txSend {

    public static final String QUEUE_NAME = "test_queue_tx";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        String msg = "Hello queue tx";
        try {
            channel.txSelect();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            int a = 10 / 0;
            System.out.println("msg:" + msg);
            channel.txCommit();
        } catch (Exception e) {
            channel.txRollback();
            System.out.println("send msg txRollback");
        } finally {
            channel.close();
            connection.close();
        }
    }
}
