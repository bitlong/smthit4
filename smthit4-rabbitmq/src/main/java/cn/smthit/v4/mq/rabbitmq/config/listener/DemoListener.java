package cn.smthit.v4.mq.rabbitmq.config.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/24  10:54
 */
@Slf4j
//@Component
//@RabbitListener(queues = "queue-sgai-gatway")
public class DemoListener {
   //@RabbitHandler
    public void onMessage(String message) {
        log.info("消费消息: " + message);
    }

}