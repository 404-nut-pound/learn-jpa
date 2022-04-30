package io.hskim.learnjpapart2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue(value = "M") // 테이블 내 코드 값처럼 처리됨
@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
@Setter(value = AccessLevel.NONE)
public class Movie extends Item {

  private String director;

  private String actor;
}
