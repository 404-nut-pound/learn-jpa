package jpa.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Movie {

  @Id
  @GeneratedValue
  private Long id;
  private String director;
  private String actor;
}
