package io.hskim.learnjpapart4.common.dto;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import lombok.Data;

@Entity
@Data
public class CommonDto {

  @Id
  @GeneratedValue
  private Long id;
}
