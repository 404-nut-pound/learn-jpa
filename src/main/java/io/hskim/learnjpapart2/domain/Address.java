package io.hskim.learnjpapart2.domain;

import javax.persistence.Embeddable;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Embeddable
@Data
@Setter(value = AccessLevel.NONE)
@Builder(toBuilder = true)
// embeddable 엔티티는 기본 생성자 스코프에 제한을 주는 것이 좋음
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class Address {

  private String city;

  private String street;

  private String zipCode;
}
