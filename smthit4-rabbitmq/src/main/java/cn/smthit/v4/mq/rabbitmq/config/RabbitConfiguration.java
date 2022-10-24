package cn.smthit.v4.mq.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @description: ...
 * @author: Bean
 * @date: 2022/10/24  10:49
 */
@Slf4j
@Configuration
public class RabbitConfiguration {

    @Bean
    public RabbitTemplate createRabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate template = new RabbitTemplate();
        template.setConnectionFactory(factory);

        template.setMandatory(true);

        template.setConfirmCallback(new RabbitTemplate.ConfirmCallback() {
            @Override
            public void confirm(CorrelationData correlationData, boolean ack, String cause) {
                log.debug("ConfirmCallback: " + correlationData + " ack: " + ack + " cause: " + cause);
            }
        });

        template.setReturnCallback(new RabbitTemplate.ReturnCallback() {
            @Override
            public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routeKey) {
                log.debug("Return Callback, Message: " + message +
                        ", replyCode: " + replyCode +
                        ", replyText: " + replyText +
                        ", exchange: " + exchange +
                        ", routeKey: " + routeKey);
            }
        });

        return template;
    }
}