package io.hskim.learnjpapart3.repository;

import io.hskim.learnjpapart3.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepostoryCustom {

  private final EntityManager em;

  @Override
  public List<Member> findMemberCustom() {
    return em
      .createQuery("select m from Member m", Member.class)
      .getResultList();
  }
}
