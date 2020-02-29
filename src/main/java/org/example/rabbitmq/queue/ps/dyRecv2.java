package org.example.rabbitmq.queue.ps;

import com.rabbitmq.client.*;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;

/**
 * @Description:订阅模式
 * @author:张士威
 * @date:2020/2/29 16:44
 * @version:
 */
public class dyRecv2 {

    public static final String QUEUE_NAME = "test_queue_fanout_sms";
    private static final String EXCHANGE_NAME = "test_exchange_fanout";
    
    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        //队列声明
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //绑定队列到交换机
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "");

        channel.basicQos(1);//保证一次只发一个

        //定义消费者
        DefaultConsumer consumer = new DefaultConsumer(channel) {
            //当队列中有消息的时候就触发这个方法
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("消费者2：" + new String(body, "utf-8"));
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    System.out.println("消费者2：done");
                    //手动回执
                    channel.basicAck(envelope.getDeliveryTag(), false);//处理完成之后需要回执发送这证明已经处理完成上一个消息
                }

            }
        };
        boolean autoAck = false;//需要关闭自动应答
        channel.basicConsume(QUEUE_NAME, autoAck, consumer);
    }

}
