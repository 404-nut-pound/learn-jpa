package io.hskim.learnjpapart2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.hskim.learnjpapart2.domain.Address;
import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.domain.Order;
import io.hskim.learnjpapart2.domain.OrderStatus;
import io.hskim.learnjpapart2.domain.item.Book;
import io.hskim.learnjpapart2.exception.NotEnoughStockException;
import io.hskim.learnjpapart2.repository.OrderRepository;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional // 테스트 코드이므로 자동 롤백 설정
@ActiveProfiles("test")
public class OrderServiceTest {
  @Autowired
  private EntityManager em;

  @Autowired
  private OrderService orderService;

  @Autowired
  private OrderRepository orderRepository;

  @Test
  void testInsertOrder() {
    Member member = extractedMember();

    Book book = extractedBook(10);

    int orderCount = 2;

    Long orderId = orderService.insertOrder(
      member.getId(),
      book.getId(),
      orderCount
    );

    Order findOrder = orderRepository.findOne(orderId);

    assertEquals(
      OrderStatus.ORDER,
      findOrder.getOrderStatus(),
      "상품 주문 기본 상태는 ORDER"
    );
    assertEquals(
      1,
      findOrder.getOrderItemList().size(),
      "상품 주문 건수가 정확해야한다."
    );
    assertEquals(
      10000 * orderCount,
      findOrder.getTotalPrice(),
      "상품 주문 총액이 정확해야한다."
    );
    assertEquals(8, book.getStockQuantity(), "재고 수량이 정확해야한다.");
  }

  @Test
  void testCancelOrder() {
    Member member = extractedMember();

    Book book = extractedBook(10);

    int orderCount = 2;

    Long orderId = orderService.insertOrder(
      member.getId(),
      book.getId(),
      orderCount
    );

    orderService.cancelOrder(orderId);

    Order findOrder = orderRepository.findOne(orderId);

    assertEquals(OrderStatus.CANCEL, findOrder.getOrderStatus());
    assertEquals(10, book.getStockQuantity());
  }

  @Test
  void overQuantity() {
    Member member = extractedMember();

    Book book = extractedBook(10);

    int orderCount = 11;

    assertThrows(
      NotEnoughStockException.class,
      () -> orderService.insertOrder(member.getId(), book.getId(), orderCount)
    );
  }

  private Book extractedBook(int stockQuantity) {
    Book book = Book
      .builder()
      .name("책 이름")
      .price(10000)
      .stockQuantity(stockQuantity)
      .build();
    em.persist(book);
    return book;
  }

  private Member extractedMember() {
    Member member = Member
      .builder()
      .name("memberA")
      .Address(
        Address
          .builder()
          .city("cityA")
          .street("streetA")
          .zipCode("zipCode")
          .build()
      )
      .build();
    em.persist(member);
    return member;
  }
}
