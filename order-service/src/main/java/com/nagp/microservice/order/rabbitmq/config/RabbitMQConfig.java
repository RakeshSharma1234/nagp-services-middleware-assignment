package com.nagp.microservice.order.rabbitmq.config;

import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Declarables;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * This class used for keeping the configuration of binding queue and exchange
 * in rabbitMQ and used by the Order Service.
 */
@Configuration
public class RabbitMQConfig {

    private static final boolean DURABLE = true;
    public final static String FANOUT_PLACE_ORDER_QUEUE_1_NAME = "com.nagp.place-order.fanout.queue1";

    public final static String FANOUT_PLACE_ORDER_QUEUE_2_NAME = "com.nagp.place-order.fanout.queue2";
    public final static String TOPIC_UPDATE_ORDER_QUEUE_NAME = "com.nagp.update-order.topic.queue";
    public final static String FANOUT_EXCHANGE_NAME = "com.nagp.place-order.fanout.exchange";
    public final static String TOPIC_EXCHANGE_NAME = "com.nagp.update-order.topic.exchange";

    @Bean
    public Declarables topicBindings() {
        Queue topicQueue = new Queue(TOPIC_UPDATE_ORDER_QUEUE_NAME, DURABLE);
        TopicExchange topicExchange = new TopicExchange(TOPIC_EXCHANGE_NAME, DURABLE, false);
        return new Declarables(topicQueue, topicExchange, BindingBuilder
          .bind(topicQueue)
          .to(topicExchange)
          .with("*"));
    }

    @Bean
    public Declarables fanoutBindings() {
        Queue fanoutQueue1 = new Queue(FANOUT_PLACE_ORDER_QUEUE_1_NAME, DURABLE);
        Queue fanoutQueue2 = new Queue(FANOUT_PLACE_ORDER_QUEUE_2_NAME, DURABLE);

        FanoutExchange fanoutExchange = new FanoutExchange(FANOUT_EXCHANGE_NAME, DURABLE, false);

        return new Declarables(fanoutQueue1, fanoutQueue2, fanoutExchange, BindingBuilder
          .bind(fanoutQueue1)
          .to(fanoutExchange), BindingBuilder
          .bind(fanoutQueue2)
          .to(fanoutExchange));
    }

}