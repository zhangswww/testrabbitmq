package org.example.rabbitmq.queue.spring.t1;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @Description:
 * @author:张士威
 * @date:2020/2/29 22:24
 * @version:
 */
public class SpringMain {

//    @Autowired
//    RabbitTemplate rabbitTemplate;

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("rabbitmq-context.xml");
        RabbitTemplate rabb = applicationContext.getBean(RabbitTemplate.class);

        rabb.convertAndSend("Hello World!");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        applicationContext.destroy();
    }
}
