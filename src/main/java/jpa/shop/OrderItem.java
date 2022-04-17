package jpa.shop;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class OrderItem {

  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;
//  @Column(name = "order_id")
//  private Long orderId;
  @ManyToOne
  @JoinColumn(name = "order_id")
  private Order order;
//  @Column(name = "item_id")
//  private Long itemId;
  @ManyToOne
  @JoinColumn(name = "item_id")
  private Item item;
  @Column(name = "order_price")
  private int orderPrice;
  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

}
