package jpa.shop;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "ORDERS")
@Data
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;
//  @Column(name = "customer_id")
//  private Long customerId;
  @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "customer_id")
  private Customer customer;
  @OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
  private List<OrderItem> orderItemList = Collections.emptyList();
  @Column(name = "order_date_time")
  private LocalDateTime orderDateTime;
  private OrderStatus status;
  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  private Delivery delivery;
}
