package io.hskim.learnjpapart3.repository;

import io.hskim.learnjpapart3.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJpaRepository {

  @PersistenceContext
  private EntityManager em;

  public Member save(final Member member) {
    em.persist(member);

    return member;
  }

  public Member findById(final Long id) {
    return em.find(Member.class, id);
  }

  public long count() {
    return em
      .createQuery("select count(m) from Member m", Long.class)
      .getSingleResult();
  }

  public List<Member> findAll() {
    return em
      .createQuery("select m from Member m", Member.class)
      .getResultList();
  }

  public List<Member> findByUserNameAndAgeGreaterThan(
    String userName,
    int age
  ) {
    return em
      .createQuery(
        "select m from Member m where m.userName = :userName and m.age > :age",
        Member.class
      )
      .setParameter("userName", userName)
      .setParameter("age", age)
      .getResultList();
  }

  /**
   * 엔티티에서 네임드 쿼리 정의 후 호출
   *
   * @param userName
   * @return
   */
  public List<Member> findByUserName(String userName) {
    return em.createNamedQuery("findByUserName", Member.class).getResultList();
  }

  public List<Member> findByPage(int offset, int limit) {
    return em
      .createQuery("select m from Member m", Member.class)
      .setFirstResult(offset)
      .setMaxResults(limit)
      .getResultList();
  }

  public long findTotalCount() {
    return em
      .createQuery("select count(m) from Member m", Long.class)
      .getSingleResult();
  }

  public int bulkAgePlus(int age) {
    return em
      .createQuery("update Member m set m.age = m.age + :age")
      .setParameter("age", age)
      .executeUpdate();
  }
}
