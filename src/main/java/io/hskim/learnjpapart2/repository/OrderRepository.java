package io.hskim.learnjpapart2.repository;

import io.hskim.learnjpapart2.domain.Order;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

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

  public List<Order> findAll() {
    return em.createQuery("select o from Order o", Order.class).getResultList();
  }

  /**
   * 일반 join을 사용하게 되면 fetch할 때마다 새로운 쿼리를 실행함
   * 해당 현상을 막기 위해 fetch join을 사용
   * FetchType.EAGER와 유사하지만 필요한 곳에만 사용할 수 있다는 장점이 있음
   * 사용 문법 - join -> join fetch
   *
   * 좀 더 최적화 하기 위해서 select 시 즉시 dto 생성자로 할당하여 처리
   * 코드 균일화를 위해서는 비권장
   * 쿼리가 복잡해서 필요해질 경우 repository 패키지가 아닌 별도로 생성하여 분리 권장
   * 사용 문법 - select new Full Package.Class name(객체.값, 객체.값.....) from 객체
   *
   * join을 실행할 경우 중복 제거를 위해 distinct 사용(Entity 단위의 distinct 수행)
   *
   * 1:다 관계에서는 페이징 불가, 컬렉션이 포함됐을 경우 1개만 사용해야 데이터 부정합을 방지할 수 있음
   * 컬렉션 데이터는 fetch join 사용 금지
   *
   * ~ToOne 관계에서는 모두 fetch join 사용
   * 컬렉션이 있을 경우 개별 조회 후 병합
   * spring.jpa.hibernate.default_batch_fetch_size 설정, 개별 설정은 엔티티의 컬렉션에 @BatchSize(int) 설정
   *
   * @param orderSearchDto
   * @return
   */
  public List<Order> findAllByCondition(OrderSearchDto orderSearchDto) {
    String jpql = "select o from Order o join fetch o.member m";
    boolean isFirstCondition = true;

    //주문 상태 검색
    if (orderSearchDto.getOrderStatus() != null) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " o.status = :status";
    }

    //회원 이름 검색
    if (StringUtils.hasText(orderSearchDto.getUserName())) {
      if (isFirstCondition) {
        jpql += " where";
        isFirstCondition = false;
      } else {
        jpql += " and";
      }
      jpql += " m.name like :name";
    }

    TypedQuery<Order> query = em
      .createQuery(jpql, Order.class)
      .setMaxResults(1000);

    if (orderSearchDto.getOrderStatus() != null) {
      query = query.setParameter("status", orderSearchDto.getOrderStatus());
    }
    if (StringUtils.hasText(orderSearchDto.getUserName())) {
      query = query.setParameter("name", orderSearchDto.getUserName());
    }

    return query.getResultList();
  }

  public List<Order> findAllWithMemberDelivery() {
    return em
      .createQuery(
        "select o from Order o" +
        " join fetch o.member m" +
        " join fetch o.delivery d",
        Order.class
      )
      .getResultList();
  }

  public List<Order> findAllWithItem() {
    return em
      .createQuery(
        "select distinct o from Order o" +
        " join fetch o.member m" +
        " join fetch o.delivery d" +
        " join fetch o.orderItems oi" +
        " join fetch oi.item i",
        Order.class
      )
      .getResultList();
  }

  public List<Order> findAllWithMemberDelivery(int offset, int limit) {
    return em
      .createQuery(
        "select o from Order o" +
        " join fetch o.member m" +
        " join fetch o.delivery d",
        Order.class
      )
      .setFirstResult(offset)
      .setMaxResults(limit)
      .getResultList();
  }
  /**
   * JPA Criteria
   */
  // public List<Order> findAllByCriteria(OrderSearchDto orderSearchDto) {
  //   CriteriaBuilder cb = em.getCriteriaBuilder();
  //   CriteriaQuery<Order> cq = cb.createQuery(Order.class);
  //   Root<Order> o = cq.from(Order.class);
  //   Join<Object, Object> m = o.join("member", JoinType.INNER);

  //   List<Predicate> criteria = new ArrayList<>();

  //   //주문 상태 검색
  //   if (orderSearchDto.getOrderStatus() != null) {
  //     Predicate status = cb.equal(
  //       o.get("status"),
  //       orderSearchDto.getOrderStatus()
  //     );
  //     criteria.add(status);
  //   }
  //   //회원 이름 검색
  //   if (StringUtils.hasText(orderSearchDto.getUserName())) {
  //     Predicate name = cb.like(
  //       m.<String>get("name"),
  //       "%" + orderSearchDto.getUserName() + "%"
  //     );
  //     criteria.add(name);
  //   }

  //   cq.where(cb.and(criteria.toArray(new Predicate[criteria.size()])));
  //   TypedQuery<Order> query = em.createQuery(cq).setMaxResults(1000);
  //   return query.getResultList();
  // }
}
