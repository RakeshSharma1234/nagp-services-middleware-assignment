package com.nagp.microservice.order.grpc.service;

import com.google.protobuf.Int64Value;
import com.nagp.microservice.order.rabbitmq.config.RabbitMQConfig;
import com.nagp.services.grpc.order.model.OrderProto;
import com.nagp.services.grpc.order.model.OrderServiceGrpc;
import io.grpc.stub.StreamObserver;
import java.util.concurrent.atomic.AtomicLong;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * gRPC service class implementation for placing and updating order requests.
 */
@GrpcService
public class OrderService extends OrderServiceGrpc.OrderServiceImplBase {

  private final AtomicLong atomicLong = new AtomicLong(1000);
  @Autowired
  private RabbitTemplate rabbitTemplate;

  /**
   * Placing Order via gRPC and
   * publishing placeOrder events to rabbitMQ via Fanout Exchange.
   *
   * @param request create order request
   * @param responseObserver responseObserver
   */
  @Override
  public void placeOrder(OrderProto.Order request, StreamObserver<Int64Value> responseObserver) {
    long orderId = atomicLong.incrementAndGet();
    String message = String.format("New Order is placed for product:%d with quantity:%d and price:%.2f and generated OrderNumber:%d",
        request.getProductId(),request.getQuantity(),request.getPrice(),orderId);
    rabbitTemplate.convertAndSend(RabbitMQConfig.FANOUT_EXCHANGE_NAME, "", message);
    responseObserver.onNext(Int64Value.of(orderId));
    responseObserver.onCompleted();
  }

  /**
   * Updating Order via gRPC and
   * publishing updateOrder events to rabbitMQ via Topic exchange.
   *
   * @param orderId orderId
   * @param responseObserver responseObserver
   */
  @Override
  public void updateOrder(Int64Value orderId, StreamObserver<Int64Value> responseObserver) {
    String message = "Order " + orderId.getValue() + " is updated successfully";
    rabbitTemplate.convertAndSend(RabbitMQConfig.TOPIC_EXCHANGE_NAME, "updated", message);
    responseObserver.onNext(orderId);
    responseObserver.onCompleted();
  }

}
