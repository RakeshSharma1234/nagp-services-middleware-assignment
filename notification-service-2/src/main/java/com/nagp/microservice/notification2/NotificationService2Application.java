package com.nagp.microservice.notification2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NotificationService2Application {
	Logger logger = LoggerFactory.getLogger(NotificationService2Application.class);

	public final static String FANOUT_PLACE_ORDER_QUEUE_NAME = "com.nagp.place-order.fanout.queue2";
	public final static String TOPIC_UPDATE_ORDER_QUEUE_NAME = "com.nagp.update-order.topic.queue";

	public static void main(String[] args) {
		SpringApplication.run(NotificationService2Application.class, args);
	}

	@RabbitListener(queues = {FANOUT_PLACE_ORDER_QUEUE_NAME})
	public void receiveMessageFromFanout(String message) {
		logger.info("Received from fanout exchange: " + message);
	}

	@RabbitListener(queues = {TOPIC_UPDATE_ORDER_QUEUE_NAME})
	public void receiveMessageFromTopic(String message) {
		logger.info("Received from topic exchange: " + message);
	}

}
