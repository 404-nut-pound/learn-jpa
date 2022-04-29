package io.hskim.learnjpapart2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "B") // 테이블 내 코드 값처럼 처리됨
@Getter
@Setter
@Builder
public class Book extends Item {

  private String author;

  private String isbn;
}
