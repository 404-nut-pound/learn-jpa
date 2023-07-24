package io.hskim.learnjpapart3.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.hskim.learnjpapart3.entity.Member;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository jpaRepository;

  @Test
  void testMember() {
    Member member = Member.builder().userName("memberA").build();

    Member saveMember = jpaRepository.save(member);

    Member findMember = jpaRepository.findById(saveMember.getId());

    assertEquals(findMember.getId(), member.getId());
    assertEquals(findMember.getUserName(), member.getUserName());
    assertEquals(findMember, member);
  }

  @Test
  void testFindByUserNameAndAgeGreaterThen() {
    Member memberA = Member.builder().userName("member").age(20).build();
    Member memberB = Member.builder().userName("member").age(30).build();

    jpaRepository.save(memberA);
    jpaRepository.save(memberB);

    List<Member> resultList = jpaRepository.findByUserNameAndAgeGreaterThan(
      "member",
      10
    );

    assertEquals(resultList.get(0).getUserName(), "member");
    assertEquals(resultList.get(0).getAge(), 20);
  }

  @Test
  void bulkAgePlus() {
    jpaRepository.save(Member.builder().userName("memberA").age(20).build());
    jpaRepository.save(Member.builder().userName("memberB").age(20).build());
    jpaRepository.save(Member.builder().userName("memberC").age(20).build());
    jpaRepository.save(Member.builder().userName("memberD").age(20).build());
    jpaRepository.save(Member.builder().userName("memberE").age(20).build());

    int resultCount = jpaRepository.bulkAgePlus(10);

    assertEquals(5, resultCount);
  }
}
