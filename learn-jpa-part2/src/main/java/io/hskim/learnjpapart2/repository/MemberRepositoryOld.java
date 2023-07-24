package io.hskim.learnjpapart2.repository;

import io.hskim.learnjpapart2.domain.Member;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryOld {

  // @PersistenceContext // Spring에서 Autowired로도 injection 지원
  private final EntityManager em;

  public void insertMember(Member member) {
    em.persist(member);
  }

  public Member findOne(Long id) {
    return em.find(Member.class, id);
  }

  public List<Member> findAll() {
    return em
      .createQuery("select m from Member m", Member.class)
      .getResultList();
  }

  public List<Member> findByName(String name) {
    return em
      .createQuery("select m from Member m where m.name = :name", Member.class)
      .setParameter("name", name)
      .getResultList();
  }
}
