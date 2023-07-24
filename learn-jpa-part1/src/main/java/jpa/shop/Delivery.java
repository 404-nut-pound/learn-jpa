package jpa.shop;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import lombok.Data;

@Entity
@Data
public class Delivery {

  @Id
  @GeneratedValue

  private Long id;
  private String city;
  private String street;
  private String zipcode;
  private DeliveryStatus status;
  @OneToOne(mappedBy = "delivery", fetch = FetchType.LAZY)
  private Order order;
}
