package org.example.rabbitmq.queue.workgp;

import com.rabbitmq.client.*;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 15:36
 * @version:
 */
public class workRacv2 {

    public static final String QUEUE_NAME = "test_work_queue";
    
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中创建通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.basicQos(1);
        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //当队列中有消息的时候就触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body, "utf-8"));
                try {
                    Thread.sleep(400);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者2：done");
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);
                }

            }
        };
        boolean autoAck = false;
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
