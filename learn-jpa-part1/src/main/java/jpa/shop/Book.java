package jpa.shop;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Book {

  @Id
  @GeneratedValue
  private Long id;
  private String author;
  private String isbn;
}
