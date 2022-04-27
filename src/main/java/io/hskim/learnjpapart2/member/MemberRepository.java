package io.hskim.learnjpapart2.member;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import io.hskim.learnjpapart2.member.vos.Member;

@Repository
public class MemberRepository {

  @PersistenceContext
  private EntityManager em;

  public Long save(Member member) {
    em.persist(member);

    return member.getId();
  }

  public Member find(Long id) {
    return em.find(Member.class, id);
  }
}
