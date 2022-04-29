package io.hskim.learnjpapart2.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import io.hskim.learnjpapart2.domain.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Builder
public class Category {

  @Id
  @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "category_item" //
      , joinColumns = @JoinColumn(name = "category_id") //
      , inverseJoinColumns = @JoinColumn(name = "item_id")) //
  private List<Item> itemList = new ArrayList<>();

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

  @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
  private List<Category> childList = new ArrayList<>();

  public void addChildCategory(Category category) {
    this.childList.add(category);
    category.setParent(this);
  }
}
