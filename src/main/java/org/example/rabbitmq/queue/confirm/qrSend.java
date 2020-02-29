package org.example.rabbitmq.queue.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:单条
 * @author:张士威
 * @date:2020/2/29 21:37
 * @version:
 */
public class qrSend {

    public static final String QUEUE_NAME = "test_queue_confirm1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();
        String msg = "Hello queue confirm1";
        int a = 10 / 0;
        channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        if (!channel.waitForConfirms()) {
            System.out.println("发送失败");
        } else {
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();
    }
}
