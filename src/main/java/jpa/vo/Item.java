package jpa.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;
  private String name;
  private int price;
  @Column(name = "stock_quantity")
  private int stockQuantity;
}
