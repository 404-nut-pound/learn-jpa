package jpa.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Album {

  @Id
  @GeneratedValue
  private Long id;
  private String artist;
  private String etc;
}
