package io.hskim.learnjpapart3.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart3.dto.MemberDto;
import io.hskim.learnjpapart3.entity.Member;
import io.hskim.learnjpapart3.entity.Team;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

  @PersistenceContext
  EntityManager em;

  @Autowired
  MemberRepository memberRepository;

  @Autowired
  TeamRepository teamRepository;

  @Test
  void testMember() {
    Member member = Member.builder().userName("memberA").build();

    Member saveMember = memberRepository.save(member);

    Optional<Member> findMember = memberRepository.findById(saveMember.getId());

    assertEquals(findMember.get().getId(), member.getId());
    assertEquals(findMember.get().getUserName(), member.getUserName());
    assertEquals(findMember.get(), member);
  }

  @Test
  void testFindByUserNameAndAgeGreaterThen() {
    Member memberA = Member.builder().userName("member").age(20).build();
    Member memberB = Member.builder().userName("member").age(30).build();

    memberRepository.save(memberA);
    memberRepository.save(memberB);

    List<Member> resultList = memberRepository.findByUserNameAndAgeGreaterThan("member", 10);

    assertEquals(resultList.get(0).getUserName(), "member");
    assertEquals(resultList.get(0).getAge(), 20);
  }

  @Test
  void testFindUserNameList() {
    Member memberA = Member.builder().userName("member").age(20).build();
    Member memberB = Member.builder().userName("member").age(30).build();

    memberRepository.save(memberA);
    memberRepository.save(memberB);

    memberRepository.flush();

    memberRepository.findUserNameList().stream().forEach(System.out::println);
  }

  @Test
  void testFindMemberDto() {
    Team team = Team.builder().teamName("teamA").build();

    teamRepository.save(team);

    Member memberA = Member.builder().userName("member").age(20).team(team).build();

    memberRepository.save(memberA);

    memberRepository.findMemberDto().stream().forEach(System.out::println);
  }

  @Test
  void testFindNames() {
    Member memberA = Member.builder().userName("memberA").age(20).build();
    Member memberB = Member.builder().userName("memberB").age(30).build();

    memberRepository.save(memberA);
    memberRepository.save(memberB);

    memberRepository.findByNames(List.of("memberA")).stream().forEach(System.out::println);
  }

  @Test
  void testOptional() {
    Member memberA = Member.builder().userName("memberA").age(20).build();

    memberRepository.save(memberA);

    Member member = memberRepository.findMemberByUserName("memberA");
    Optional<Member> optionalMember = memberRepository.findOptinalMemberByUserName("memberA");

    assertEquals(member, optionalMember.get());
  }

  @Test
  void paging() {
    memberRepository.save(Member.builder().userName("memberA").age(20).build());
    memberRepository.save(Member.builder().userName("memberB").age(20).build());
    memberRepository.save(Member.builder().userName("memberC").age(20).build());
    memberRepository.save(Member.builder().userName("memberD").age(20).build());
    memberRepository.save(Member.builder().userName("memberE").age(20).build());

    int age = 20;

    PageRequest pageRequest = PageRequest.of(0, 2, Sort.by(Sort.Direction.DESC, "userName"));

    Page<Member> pagedMember = memberRepository.findPagedMemberByAge(pageRequest, age);

    // Page 클래스 속성
    List<Member> content = pagedMember.getContent();
    long totalCount = pagedMember.getTotalElements();

    content.stream().forEach(System.out::println);
    System.out.println("Total Count : %d".formatted(totalCount));
    System.out.println("Current Page : %d".formatted(pagedMember.getNumber()));
    System.out.println("Total Pages : %d".formatted(pagedMember.getTotalPages()));
    System.out.println("Is First? - %s".formatted(pagedMember.isFirst()));
    System.out.println("Has Next? - %s".formatted(pagedMember.hasNext()));

    // 속성 유지하면서 Dto로 변환
    Page<MemberDto> pagedMemberDto = pagedMember
        .map(member -> new MemberDto(member.getId(), member.getUserName(), null));
  }

  @Test
  void bulkAgePlus() {
    memberRepository.save(Member.builder().userName("memberA").age(20).build());
    memberRepository.save(Member.builder().userName("memberB").age(20).build());
    memberRepository.save(Member.builder().userName("memberC").age(20).build());
    memberRepository.save(Member.builder().userName("memberD").age(20).build());
    memberRepository.save(Member.builder().userName("memberE").age(20).build());

    int resultCount = memberRepository.bulkAgePlus(10);
    // clear를 실행하지 않으면 영속성 컨텍스트는 변경되지 않음

    assertEquals(5, resultCount);
  }

  @Test
  void findMemberLazy() {
    Team teamA = Team.builder().teamName("teamA").build();
    Team teamB = Team.builder().teamName("teamB").build();

    teamRepository.save(teamA);
    teamRepository.save(teamB);

    Member memberA = Member.builder().userName("memberA").age(20).team(teamA).build();
    Member memberB = Member.builder().userName("memberB").age(20).team(teamB).build();

    memberRepository.save(memberA);
    memberRepository.save(memberB);

    em.flush();
    em.clear();

    List<Member> memberList = memberRepository.findAll();

    memberList.stream().forEach(member -> System.out.println("member name - %s".formatted(member.getUserName())));
    System.out.println();
    memberList.stream()
        .forEach(member -> System.out.println("member team name - %s".formatted(member.getTeam().getTeamName())));
  }

  @Test
  void queryHint() {
    Member member = Member.builder().userName("memberA").age(20).build();

    memberRepository.save(member);
    em.flush();
    em.clear();

    Member findMember = memberRepository.findById(member.getId()).get();
    findMember.setUserName("member2");

    em.flush();

    Member findReadOnlyMember = memberRepository.findById(member.getId()).get();
    findReadOnlyMember.setUserName("member2");

    em.flush();
  }

}
