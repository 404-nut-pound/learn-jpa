package io.hskim.learnjpapart2.repository;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Repository;

import io.hskim.learnjpapart2.domain.Order;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OrderRepository {

  private final EntityManager em;

  public void insertOrder(Order order) {
    em.persist(order);
  }

  public Order findOne(Long id) {
    return em.find(Order.class, id);
  }

}
