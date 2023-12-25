package com.nagp.microservice.product.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.protobuf.Int64Value;
import com.nagp.microservice.product.dtos.OrderDTO;
import com.nagp.microservice.product.grpc.client.OrderClient;
import com.nagp.services.grpc.order.model.OrderProto;
import java.io.IOException;
import java.io.InputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/product")
public class ProductController {

  @Autowired
  private OrderClient orderClient;

  @GetMapping("/list")
  public ResponseEntity<Object> getProducts() throws IOException {
    ObjectMapper mapper = new ObjectMapper();
    InputStream inputStream = TypeReference.class.getResourceAsStream("/products.json");
    Object products =  mapper.readValue(inputStream,Object.class);
    ResponseEntity<Object> entity = new ResponseEntity<>(products, HttpStatus.OK);
    return entity;
  }
  @PostMapping(path = "/placeOrder")
  public ResponseEntity<String> placeOrder(@RequestBody OrderDTO orderDTO) {
    OrderProto.Order order = OrderProto.Order.newBuilder().setPrice(orderDTO.getPrice()).
        setProductId(orderDTO.getProductId()).setQuantity(orderDTO.getQuantity()).build();

    //grpc call to order service
    Int64Value responseOrderId = orderClient.placeOrder(order);

    String message = String.format("New Order is placed for product:%d with quantity:%d and price:%.2f and generated OrderNumber:%d",
        orderDTO.getProductId(),orderDTO.getQuantity(),orderDTO.getPrice(),responseOrderId.getValue());
    ResponseEntity<String> entity = new ResponseEntity<>(message, HttpStatus.CREATED);
    return entity;
  }

  @PutMapping(path = "/updateOrder/{orderId}")
  public ResponseEntity<String> updateOrder(@PathVariable long orderId) {
    //grpc call to order service
    Int64Value responseOrderId = orderClient.updateOrder(Int64Value.of(orderId));

    String message = "Order " + responseOrderId.getValue() + " is updated successfully";
    ResponseEntity<String> entity = new ResponseEntity<>(message, HttpStatus.OK);
    return entity;
  }
}
