package com.nagp.microservice.notification1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *
 * Notification Service 1 listening message for place order events
 */
@SpringBootApplication
public class NotificationService1Application {
	Logger logger = LoggerFactory.getLogger(NotificationService1Application.class);

	public final static String FANOUT_PLACE_ORDER_QUEUE_NAME = "com.nagp.place-order.fanout.queue1";

	public static void main(String[] args) {
		SpringApplication.run(NotificationService1Application.class, args);
	}

	/**
	 * Listing message from the Fanout Exchange for order creation event.
	 * @param message queue message
	 */
	@RabbitListener(queues = {FANOUT_PLACE_ORDER_QUEUE_NAME})
	public void receiveMessageFromFanout(String message) {
		logger.info("Received from fanout exchange: " + message);
	}
}
