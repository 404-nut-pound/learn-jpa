package io.hskim.learnjpapart3.repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import io.hskim.learnjpapart3.entity.Member;

@Repository
public class MemberJpaRepository {

  @PersistenceContext
  private EntityManager em;

  public Member save(final Member member) {
    em.persist(member);

    return member;
  }

  public Member find(final Long id) {
    return em.find(Member.class, id);
  }
}
