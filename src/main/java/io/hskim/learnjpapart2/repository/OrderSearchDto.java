package io.hskim.learnjpapart2.repository;

import io.hskim.learnjpapart2.domain.OrderStatus;
import lombok.Data;

@Data
public class OrderSearchDto {
  private String userName;

  private OrderStatus orderStatus;
}
