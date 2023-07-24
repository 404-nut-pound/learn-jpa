package io.hskim.learnjpapart4.repository;

import static io.hskim.learnjpapart4.entity.QMember.member;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

  @Autowired
  EntityManager em;

  @Autowired
  MemberRepository memberRepository;

  @Test
  void basicTest() {
    Member member = Member.builder().userName("memberA").age(10).build();

    memberRepository.save(member);

    Member findMember = memberRepository.findById(member.getId()).get();

    assertEquals(member, findMember);

    List<Member> memberList = memberRepository.findByUserName(
      member.getUserName()
    );

    assertEquals(member, memberList.get(0));
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

    List<MemberTeamDto> memberTeamDtoList = memberRepository.search(condition);

    assertEquals(memberTeamDtoList.size(), 4);

    PageRequest pageRequest = PageRequest.of(1, 3);

    Page<MemberTeamDto> pagedResult = memberRepository.searchPageSimple(
      MemberSearchCondition.builder().build(),
      pageRequest
    );

    assertEquals(pagedResult.getSize(), 3);
  }

  //QuerydslPredicateExecutor는 join을 못 함
  @Test
  void querydslPredicateExecutorTest() {
    Iterable<Member> memberIter = memberRepository.findAll(
      member.age.between(20, 40)
    );

    for (Member member : memberIter) {
      System.out.println(member);
    }
  }
}
