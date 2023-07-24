package io.hskim.learnjpapart4.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import io.hskim.learnjpapart4.entity.Member;
import io.hskim.learnjpapart4.entity.Team;
import java.util.List;
import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
@ActiveProfiles("test")
public class MemberJpaRepositoryTest {

  @Autowired
  EntityManager em;

  @Autowired
  MemberJpaRepository memberJpaRepository;

  @Test
  void basicTest() {
    Member member = Member.builder().userName("memberA").age(10).build();

    memberJpaRepository.save(member);

    Member findMember = memberJpaRepository.findById(member.getId()).get();

    assertEquals(member, findMember);

    List<Member> memberList = memberJpaRepository.findByUserName(
      member.getUserName()
    );

    assertEquals(member, memberList.get(0));

    List<Member> queryDslmemberList = memberJpaRepository.findByUserNameQueryDsl(
      member.getUserName()
    );

    assertEquals(member, queryDslmemberList.get(0));
  }

  @Test
  void searchTest() {
    Team teamA = Team.builder().teamName("teamA").build();
    Team teamB = Team.builder().teamName("teamB").build();

    em.persist(teamA);
    em.persist(teamB);

    Member memberA = Member
      .builder()
      .userName("memberA")
      .age(10)
      .team(teamA)
      .build();
    Member memberB = Member
      .builder()
      .userName("memberB")
      .age(20)
      .team(teamB)
      .build();
    Member memberC = Member
      .builder()
      .userName("memberC")
      .age(30)
      .team(teamA)
      .build();
    Member memberD = Member
      .builder()
      .userName("memberD")
      .age(40)
      .team(teamB)
      .build();

    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    em.persist(memberD);

    MemberSearchCondition condition = MemberSearchCondition
      .builder()
      .teamName("teamB")
      .ageGoe(35)
      .ageLoe(40)
      .build();

    // List<MemberTeamDto> memberTeamDtoList = memberJpaRepository.searchByBuilder(
    //   condition
    // );

    List<MemberTeamDto> memberTeamDtoList = memberJpaRepository.search(
      condition
    );

    assertEquals(memberTeamDtoList.size(), 4);
  }
}
