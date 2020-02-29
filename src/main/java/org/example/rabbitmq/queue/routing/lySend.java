package org.example.rabbitmq.queue.routing;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:路由模式
 * @author:张士威
 * @date:2020/2/29 20:37
 * @version:
 */
public class lySend {

    private static final String EXCHANGE_NAME = "test_exchange_direct";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "direct");//direct 声明为路由模式
        String msg = "hello direct!";
        String ruotingKey = "info";
        channel.basicPublish(EXCHANGE_NAME, ruotingKey, null, msg.getBytes());
        channel.close();
        connection.close();
    }
}
