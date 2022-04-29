package io.hskim.learnjpapart2.domain;

import javax.persistence.Embeddable;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@Builder
public class Address {

  private String city;

  private String street;

  private String zipCode;
}
