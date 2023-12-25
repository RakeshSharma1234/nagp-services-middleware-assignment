package com.nagp.microservice.notification2;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Notification Service 2 listening message for place / update order events.
 */
@SpringBootApplication
public class NotificationService2Application {
	Logger logger = LoggerFactory.getLogger(NotificationService2Application.class);

	public final static String FANOUT_PLACE_ORDER_QUEUE_NAME = "com.nagp.place-order.fanout.queue2";
	public final static String TOPIC_UPDATE_ORDER_QUEUE_NAME = "com.nagp.update-order.topic.queue";

	public static void main(String[] args) {
		SpringApplication.run(NotificationService2Application.class, args);
	}

	/**
	 * Listing message from the Fanout Exchange for order creation event.
	 * @param message queue message
	 */
	@RabbitListener(queues = {FANOUT_PLACE_ORDER_QUEUE_NAME})
	public void receiveMessageFromFanout(String message) {
		logger.info("Received from fanout exchange: " + message);
	}

	/**
	 * Listing message from the Topic Exchange for order update event.
	 * @param message queue message
	 */
	@RabbitListener(queues = {TOPIC_UPDATE_ORDER_QUEUE_NAME})
	public void receiveMessageFromTopic(String message) {
		logger.info("Received from topic exchange: " + message);
	}

}
