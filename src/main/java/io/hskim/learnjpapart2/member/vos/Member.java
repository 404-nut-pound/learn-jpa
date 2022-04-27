package io.hskim.learnjpapart2.member.vos;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Data;

@Entity
@Data
@Builder
public class Member {
  @Id
  @GeneratedValue
  private Long id;
  private String userName;
}
