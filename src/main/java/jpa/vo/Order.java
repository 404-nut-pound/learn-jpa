package jpa.vo;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
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
  @Column(name = "member_id")
  private Long memberId;
  @Column(name = "order_date_time")
  private LocalDateTime orderDateTime;
  private OrderStatus status;
}
