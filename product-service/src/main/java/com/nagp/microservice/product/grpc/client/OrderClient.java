package com.nagp.microservice.product.grpc.client;

import com.google.protobuf.Int64Value;
import com.nagp.services.grpc.order.model.OrderProto;
import com.nagp.services.grpc.order.model.OrderServiceGrpc;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class OrderClient {

  Logger logger = LoggerFactory.getLogger(OrderClient.class);

  @GrpcClient("order-service-grpc")
  OrderServiceGrpc.OrderServiceBlockingStub stub;

  public Int64Value placeOrder(OrderProto.Order order) {
    try {
      return stub.placeOrder(order);
    } catch (final StatusRuntimeException e) {
      logger.error("Error in communication", e);
      return null;
    }
  }

  public Int64Value updateOrder(Int64Value orderId) {
    try {
      return stub.updateOrder(orderId);
    } catch (final StatusRuntimeException e) {
      logger.error("Error in communication", e);
      return null;
    }
  }
}