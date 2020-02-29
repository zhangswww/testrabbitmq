package org.example.rabbitmq.queue.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import org.example.rabbitmq.util.ConnectionUtils;

import java.io.IOException;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 21:56
 * @version:
 */
public class qrSend3 {

    public static final String QUEUE_NAME = "test_queue_confirm3";

    public static void main(String[] args) throws Exception {
        Connection connection = ConnectionUtils.getConnection();
        Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        //生产者调用confirmSelect将channel设置为confirm模式
        channel.confirmSelect();
        //未确认的消息标识
        final SortedSet<Long> confirmSet = Collections.synchronizedSortedSet(new TreeSet<Long>());

        //通道添加监听
        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("-----多个成功-----");
                    confirmSet.headSet(l + 1).clear();//成功了就把集合中的成功数据清除
                } else {
                    System.out.println("-----单个成功-----");
                    confirmSet.remove(l);
                }
            }

            @Override
            public void handleNack(long l, boolean b) throws IOException {
                if (b) {
                    System.out.println("-----多个失败-----");
                    confirmSet.headSet(l + 1).clear();//成功了就把集合中的成功数据清除
                } else {
                    System.out.println("-----单个失败-----");
                    confirmSet.remove(l);
                }
            }
        });
        String msg = "asnfklanfkla";
        while (true) {
            long seqNo = channel.getNextPublishSeqNo();
            channel.basicPublish("", QUEUE_NAME, null, msg.getBytes());
            confirmSet.add(seqNo);
        }
    }
}
