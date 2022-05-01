package io.hskim.learnjpapart2.domain.item;

import io.hskim.learnjpapart2.domain.Category;
import io.hskim.learnjpapart2.exception.NotEnoughStockException;
import java.util.ArrayList;
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
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE) // 상속 받는 클래스에 대해 테이블 전략 설정
@DiscriminatorColumn(name = "dtype") // 상속 받는 클래스에 식별자 컬럼 설정, 기본 값 "DTYPE"
@Data
@Setter(value = AccessLevel.PUBLIC)
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;

  private int price;

  private int stockQuantity;

  @ManyToMany(mappedBy = "itemList", fetch = FetchType.LAZY)
  // List 타입은 초기화를 미리 하는 것이 좋음
  private List<Category> categoryList = new ArrayList<>();

  // setter 사용하지 말고 객체 내 메소드로 조작
  /**
   * 재고 추가
   *
   * @param stockQuantity
   */
  public void addStockQuantity(int stockQuantity) {
    this.stockQuantity += stockQuantity;
  }

  /**
   * 재고 감소
   *
   * @param stockQuantity
   */
  public void removeStockQunatity(int stockQuantity) {
    int restStockQuantity = this.stockQuantity - stockQuantity;

    if (restStockQuantity < 0) {
      throw new NotEnoughStockException("Not Enough Stock Quantity!");
    }

    this.stockQuantity = restStockQuantity;
  }
}
