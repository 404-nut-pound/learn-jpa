package io.hskim.learnjpapart2.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "orders")
@Getter
@Setter
public class Order {

  @Id
  @GeneratedValue
  @Column(name = "order_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "member_id")
  // 연관 관계의 주도권이 있는 곳에 @JoinColumn 설정
  // 주로 외래키가 있는 쪽에 주도권 설정
  private Member member;

  // cascade는 연관된 객체를 같이 조작해줌
  @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  private List<OrderItem> orderItemList = new ArrayList<>();

  @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "delivery_id")
  // 1:1 관계의 경우 자주 조회되는 곳에 외래키 설정
  private Delivery delivery;

  private LocalDateTime orderDateTime;

  @Enumerated(EnumType.STRING)
  private OrderStatus orderStatus;

  /**
   * 연관 관계 편의 메소드 입력
   */
  public void setMember(Member member) {
    this.member = member;
    member.getOrderList().add(this);
  }

  public void setDelivery(Delivery delivery) {
    this.delivery = delivery;
    delivery.setOrder(this);
  }
}
