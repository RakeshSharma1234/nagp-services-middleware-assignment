package com.nagp.microservice.product.dtos;

import lombok.Data;

/**
 * Place Order Request
 */
@Data
public class OrderDTO {
  private long productId;
  private double price;
  private long quantity;
}
