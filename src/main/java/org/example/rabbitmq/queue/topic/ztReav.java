package org.example.rabbitmq.queue.topic;

import com.rabbitmq.client.*;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Description:路由模式
 * @author:张士威
 * @date:2020/2/29 20:40
 * @version:
 */
public class ztReav {

    private static final String EXCHANGE_NAME = "test_exchange_topic";
    public static final String QUEUE_NAME = "test_queue_topic1";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.add");//绑定路由key
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "goods.update");//绑定路由key
        channel.basicQos(1);

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
