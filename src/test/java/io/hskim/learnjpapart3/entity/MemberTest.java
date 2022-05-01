package io.hskim.learnjpapart3.entity;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberTest {

  @PersistenceContext
  EntityManager em;

  @Test
  void testEntity() {
    Team teamA = Team.builder().teamName("teamA").build();
    Team teamB = Team.builder().teamName("teamB").build();

    em.persist(teamA);
    em.persist(teamB);

    Member memberA = Member.builder().userName("memberA").age(20).build();
    memberA.changeTeam(teamA);
    Member memberB = Member.builder().userName("memberB").age(20).build();
    memberB.changeTeam(teamA);
    Member memberC = Member.builder().userName("memberC").age(20).build();
    memberC.changeTeam(teamB);
    Member memberD = Member.builder().userName("memberD").age(20).build();
    memberD.changeTeam(teamB);

    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    em.persist(memberD);

    em.flush();
    em.clear();

    List<Member> memberList = em.createQuery("select m from Member m", Member.class).getResultList();

    for (Member member : memberList) {
      System.out.println("Member - %s".formatted(member));
      System.out.println("Team in Member - %s".formatted(member.getTeam()));
      System.out.println();
    }
  }
}
