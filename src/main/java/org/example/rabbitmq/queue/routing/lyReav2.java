package org.example.rabbitmq.queue.routing;

import com.rabbitmq.client.*;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

/**
 * @Description:路由模式
 * @author:张士威
 * @date:2020/2/29 20:40
 * @version:
 */
public class lyReav2 {

    private static final String EXCHANGE_NAME = "test_exchange_direct";
    public static final String QUEUE_NAME = "test_queue_direct2";

    public static void main(String[] args) throws IOException, TimeoutException {
        Connection connection = ConnectionUtils.getConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "error");//绑定路由key
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "info");//绑定路由key
        channel.queueBind(QUEUE_NAME, EXCHANGE_NAME, "warning");//绑定路由key 绑定了三个
        channel.basicQos(1);

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
