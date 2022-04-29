package io.hskim.learnjpapart2.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
// embeddable 엔티티는 기본 생성자 스코프에 제한을 주는 것이 좋음
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

  private String city;

  private String street;

  private String zipCode;
}
