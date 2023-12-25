package com.nagp.microservice.product.dtos;

import lombok.Data;

@Data
public class OrderDTO {
  private long productId;
  private double price;
  private long quantity;
}
