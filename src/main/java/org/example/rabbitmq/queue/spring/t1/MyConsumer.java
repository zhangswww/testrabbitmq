package org.example.rabbitmq.queue.spring.t1;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 22:23
 * @version:
 */
public class MyConsumer {

    //执行具体的业务方法
    public void listen(String foo) {
        System.out.println("消费者:" + foo);
    }
}
