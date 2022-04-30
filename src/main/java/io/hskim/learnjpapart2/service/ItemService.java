package io.hskim.learnjpapart2.service;

import io.hskim.learnjpapart2.controller.BookForm;
import io.hskim.learnjpapart2.domain.item.Item;
import io.hskim.learnjpapart2.repository.ItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {
  private final ItemRepository itemRepository;

  @Transactional
  public void insertItem(Item item) {
    itemRepository.insertItem(item);
  }

  // merge는 모든 값을 대체함
  // 준영속 객체를 사용하여 merge 처리할 경우 빈 값은 null로 대체됨
  // 해당 현상을 피하기 위해 영속성 컨텍스트의 변경 감지 기능을 사용
  @Transactional
  public void updateItem(BookForm bookForm) {
    Item item = itemRepository.findOne(bookForm.getId());
    item.setName(bookForm.getName());
    item.setPrice(bookForm.getPrice());
    item.setStockQuantity(bookForm.getStockQuantity());
  }

  public Item selectItemById(Long id) {
    return itemRepository.findOne(id);
  }

  public List<Item> selectItemAll() {
    return itemRepository.findAll();
  }
}
