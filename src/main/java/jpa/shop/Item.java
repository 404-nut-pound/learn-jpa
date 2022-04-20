package jpa.shop;

import java.util.Collections;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import lombok.Data;

@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 기본 값 단일 테이블
@DiscriminatorColumn(name = "DTYPE") // 구분자 컬럼 추가, 기본 값 DTYPE
@Data
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;
  private String name;
  private int price;
  @Column(name = "stock_quantity")
  private int stockQuantity;
  @ManyToMany(mappedBy = "itemList", fetch = FetchType.LAZY)
  private List<Category> categoryList = Collections.emptyList();
}
