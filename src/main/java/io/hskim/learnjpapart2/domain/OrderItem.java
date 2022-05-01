package io.hskim.learnjpapart2.domain;

import io.hskim.learnjpapart2.domain.item.Item;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Data
@Setter(value = AccessLevel.NONE)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
  @Id
  @GeneratedValue
  @Column(name = "order_item_id")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "item_id")
  private Item item;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "order_id")
  private Order order;

  private int orderPrice;

  private int orderCount;

  /**
   * 주문 상품 정보 생성
   *
   * @param item
   * @param orderPrice
   * @param orderCount
   * @return
   */
  public static OrderItem createOrderItem(
    Item item,
    int orderPrice,
    int orderCount
  ) {
    item.removeStockQunatity(orderCount);

    return OrderItem
      .builder()
      .item(item)
      .orderPrice(orderPrice)
      .orderCount(orderCount)
      .build();
  }

  /**
   * 주문 취소시 재고 수량 증가
   */
  public void cancelOrderItem() {
    this.item.addStockQuantity(this.orderCount);
  }

  /**
   * 주문 총액 계싼
   *
   * @return
   */
  public int getTotalPrice() {
    return this.orderCount * this.orderPrice;
  }

  public void setOrder(Order order) {
    this.order = order;
  }
}
