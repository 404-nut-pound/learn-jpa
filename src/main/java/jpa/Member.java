package jpa;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Member {

  @Id
  private Long id;
  private String name;
}
