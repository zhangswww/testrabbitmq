package org.example.rabbitmq.queue.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

/**
 * @Description:批量：一次发送一批，如果有一条错了，其他的都会丢失
 * @author:张士威
 * @date:2020/2/29 21:41
 * @version:
 */
public class qrSend2 {

    public static final String QUEUE_NAME = "test_queue_confirm2";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();
        String msg;
        for (int i = 0; i < 10; i++) {
            msg = "Hello queue confirm" + i;
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
        }//发完之后再确认
        if (!channel.waitForConfirms()) {
            System.out.println("发送失败");
        } else {
            System.out.println("发送成功");
        }
        channel.close();
        connection.close();
    }
}
