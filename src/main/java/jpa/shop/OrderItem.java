package jpa.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;
  @Column(name = "order_id")
  private Long orderId;
  @Column(name = "item_id")
  private Long itemId;
  @Column(name = "order_price")
  private int orderPrice;
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

}
