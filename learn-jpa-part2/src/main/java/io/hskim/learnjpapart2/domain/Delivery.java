package io.hskim.learnjpapart2.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
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
public class Delivery {

  @Id
  @GeneratedValue
  @Column(name = "delivery_id")
  private Long id;

  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;

  private Address address;

  @Enumerated(EnumType.STRING)
  private DeliveryStatus deliveryStatus;

  public void setOrder(Order order) {
    this.order = order;
  }
}
