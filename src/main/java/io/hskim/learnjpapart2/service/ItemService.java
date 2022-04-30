package io.hskim.learnjpapart2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart2.domain.item.Item;
import io.hskim.learnjpapart2.repository.ItemRepository;
import lombok.RequiredArgsConstructor;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void insertItem(Item item) {
    itemRepository.insertItem(item);
  }

  public Item selectItemById(Long id) {
    return itemRepository.findOne(id);
  }

  public List<Item> selectItemAll() {
    return itemRepository.findAll();
  }
}
