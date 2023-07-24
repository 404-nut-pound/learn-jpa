package io.hskim.learnjpapart2.service;

import io.hskim.learnjpapart2.domain.Delivery;
import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.domain.Order;
import io.hskim.learnjpapart2.domain.OrderItem;
import io.hskim.learnjpapart2.domain.item.Item;
import io.hskim.learnjpapart2.repository.ItemRepository;
import io.hskim.learnjpapart2.repository.MemberRepositoryOld;
import io.hskim.learnjpapart2.repository.OrderRepository;
import io.hskim.learnjpapart2.repository.OrderSearchDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class OrderService {

  private final OrderRepository orderRepository;
  private final MemberRepositoryOld memberRepository;
  private final ItemRepository itemRepository;

  @Transactional
  public Long insertOrder(Long memberId, Long itemId, int orderCount) {
    // 주문자 및 주문 상품 정보 조회
    Member member = memberRepository.findOne(memberId);
    Item item = itemRepository.findOne(itemId);

    // 배송 정보 생성
    Delivery delivery = Delivery.builder().address(member.getAddress()).build();

    // 주문 상품 생성
    OrderItem orderItem = OrderItem.createOrderItem(
      item,
      item.getPrice(),
      orderCount
    );

    // 주문 정보 생성
    Order order = Order.createOrder(member, delivery, orderItem);

    // 주문 저장
    orderRepository.insertOrder(order);

    return order.getId();
  }

  @Transactional
  public void cancelOrder(Long orderId) {
    orderRepository.findOne(orderId).cancelOrder();
  }

  public List<Order> selectOrderList(OrderSearchDto orderSearchDto) {
    return orderRepository.findAllByCondition(orderSearchDto);
  }
}
