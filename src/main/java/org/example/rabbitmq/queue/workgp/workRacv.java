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
public class workRacv {

    public static final String QUEUE_NAME = "test_work_queue";
    
    public static void main(String[] args) throws Exception {
        // 获取到连接以及mq通道
        Connection connection = ConnectionUtils.getConnection();
        // 从连接中创建通道
        final Channel channel = connection.createChannel();
        // 声明队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        channel.basicQos(1);//保证一次只发一个

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //当队列中有消息的时候就触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者1：" + new String(body, "utf-8"));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者1：done");
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);//处理完成之后需要回执发送这证明已经处理完成上一个消息
                }

            }
        };
        boolean autoAck = false;//需要关闭自动应答
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }
}
