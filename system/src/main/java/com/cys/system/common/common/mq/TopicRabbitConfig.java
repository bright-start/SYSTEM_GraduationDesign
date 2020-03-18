package com.cys.system.common.common.mq;

import com.cys.system.common.config.Config;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class TopicRabbitConfig {
    final static String message = "product_message_queue";

    @Bean(name = "goods")
    public Queue queueMessage(){
        return new Queue(message);
    }


    /**
     * 声明一个Topic类型的交换机
     */
    @Bean
    TopicExchange exchange(){
        return new TopicExchange(Config.EXCHANGE);
    }

    /**
     * 绑定Queue到交换机，并且指定routingKey
     * @param goods  商品消息队列
     * @param exchange
     * @return
     */
    @Bean
    Binding bindingExchangeMessage(Queue goods, TopicExchange exchange){
        return BindingBuilder.bind(goods).to(exchange).with(Config.ROUTING_KEY);
    }

}
