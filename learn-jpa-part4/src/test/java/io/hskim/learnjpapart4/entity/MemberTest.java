package io.hskim.learnjpapart4.entity;

import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {

  @Autowired
  EntityManager em;

  @Test
  void testEntity() {
    Team teamA = Team.builder().teamName("teamA").build();
    Team teamB = Team.builder().teamName("teamB").build();

    em.persist(teamA);
    em.persist(teamB);

    Member memberA = Member
      .builder()
      .userName("MemberA")
      .age(20)
      .team(teamA)
      .build();
    Member memberB = Member
      .builder()
      .userName("MemberB")
      .age(20)
      .team(teamB)
      .build();
    Member memberC = Member
      .builder()
      .userName("MemberC")
      .age(20)
      .team(teamA)
      .build();
    Member memberD = Member
      .builder()
      .userName("MemberD")
      .age(20)
      .team(teamB)
      .build();

    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    em.persist(memberD);

    em.flush();
    em.clear();

    List<Member> memberList = em
      .createQuery("select m from Member m", Member.class)
      .getResultList();

    memberList.stream().forEach(System.out::println);
  }
}
