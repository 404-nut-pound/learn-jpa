package io.hskim.learnjpapart2.domain.item;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue(value = "A") // 테이블 내 코드 값처럼 처리됨
@Getter
@Setter
@Builder
public class Album extends Item {

  private String artist;

  private String etc;
}
