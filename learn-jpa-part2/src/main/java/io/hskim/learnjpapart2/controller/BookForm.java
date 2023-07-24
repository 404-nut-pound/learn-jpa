package io.hskim.learnjpapart2.controller;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookForm {
  private Long id;

  private String name;

  private int price;

  private int stockQuantity;

  private String author;

  private String isbn;
}
