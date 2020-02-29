package org.example.rabbitmq.queue.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 21:01
 * @version:
 */
public class ztSend {

    private static final String EXCHANGE_NAME = "test_exchange_topic";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "topic");//direct 声明为路由模式
        String msg = "hello ShangPing!";
        String ruotingKey = "goods.select";
        channel.basicPublish(EXCHANGE_NAME, ruotingKey, null, msg.getBytes());
        System.out.println("msg:"+msg);
        channel.close();
        connection.close();
    }
}
