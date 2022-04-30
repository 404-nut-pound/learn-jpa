package io.hskim.learnjpapart2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "M") // 테이블 내 코드 값처럼 처리됨
@Data
@Setter(value = AccessLevel.NONE)
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = false)
public class Movie extends Item {

  private String director;

  private String actor;
}
