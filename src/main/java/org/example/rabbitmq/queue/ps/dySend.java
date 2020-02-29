package org.example.rabbitmq.queue.ps;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:订阅模式
 * @author:张士威
 * @date:2020/2/29 16:35
 * @version:
 */
public class dySend {

    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    //如果没有绑定队列，直接发送消息会丢失，因为交换机没有存储能力，在rabbitmq中只有队列有存储能力
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        //声明交换机
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        //发送消息
        String msg = "Hello exchange";
        channel.basicPublish(EXCHANGE_NAME, "", null, msg.getBytes());
        System.out.println("msg: " + msg);
        channel.close();
        connection.close();
    }
}
