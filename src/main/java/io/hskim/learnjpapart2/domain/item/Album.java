package io.hskim.learnjpapart2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@DiscriminatorValue(value = "A") // 테이블 내 코드 값처럼 처리됨
@Data
@Setter(value = AccessLevel.NONE)
@EqualsAndHashCode(callSuper = false)
@SuperBuilder(toBuilder = true)
public class Album extends Item {

  private String artist;

  private String etc;
}
