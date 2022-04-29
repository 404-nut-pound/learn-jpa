package io.hskim.learnjpapart2.domain.item;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToMany;

import io.hskim.learnjpapart2.domain.Category;
import lombok.Getter;
import lombok.Setter;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 받는 클래스에 대해 테이블 전략 설정
@DiscriminatorColumn(name = "dtype") // 상속 받는 클래스에 식별자 컬럼 설정, 기본 값 "DTYPE"
@Getter
@Setter
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;

  private int price;

  private int stockQuantity;

  @ManyToMany(mappedBy = "itemList")
  private List<Category> categoryList;
}
