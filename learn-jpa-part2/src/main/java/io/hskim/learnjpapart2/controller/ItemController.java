package io.hskim.learnjpapart2.controller;

import io.hskim.learnjpapart2.domain.item.Book;
import io.hskim.learnjpapart2.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/items")
@RequiredArgsConstructor
public class ItemController {
  private final ItemService itemService;

  @GetMapping(value = "/new")
  public String getCreateItemForm(Model model) {
    model.addAttribute("form", BookForm.builder().build());

    return "items/createItemForm";
  }

  @PostMapping(value = "/new")
  public String postCreateItemForm(BookForm bookForm) {
    Book book = Book
      .builder()
      .name(bookForm.getName())
      .price(bookForm.getPrice())
      .stockQuantity(bookForm.getStockQuantity())
      .author(bookForm.getAuthor())
      .isbn(bookForm.getIsbn())
      .build();

    itemService.insertItem(book);

    return "redirect:/";
  }

  @GetMapping
  public String getItemList(Model model) {
    model.addAttribute("itemList", itemService.selectItemAll());

    return "items/itemList";
  }

  @GetMapping(value = "{itemId}/edit")
  public String getUpdateItemForm(@PathVariable Long itemId, Model model) {
    Book book = (Book) itemService.selectItemById(itemId);

    BookForm bookForm = BookForm
      .builder()
      .id(book.getId())
      .name(book.getName())
      .price(book.getPrice())
      .stockQuantity(book.getStockQuantity())
      .author(book.getAuthor())
      .isbn(book.getIsbn())
      .build();

    model.addAttribute("form", bookForm);

    return "items/updateItemForm";
  }

  @PostMapping(value = "{itemId}/edit")
  public String postUpdateItemForm(
    @ModelAttribute(name = "form") BookForm bookForm
  ) {
    // Book book = Book
    //   .builder()
    //   .id(bookForm.getId())
    //   .name(bookForm.getName())
    //   .price(bookForm.getPrice())
    //   .stockQuantity(bookForm.getStockQuantity())
    //   .author(bookForm.getAuthor())
    //   .isbn(bookForm.getIsbn())
    //   .build();

    // itemService.insertItem(book);
    itemService.updateItem(bookForm);

    return "redirect:/items";
  }
}
