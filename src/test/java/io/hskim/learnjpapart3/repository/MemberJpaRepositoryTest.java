package io.hskim.learnjpapart3.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart3.entity.Member;

@SpringBootTest
@Transactional
public class MemberJpaRepositoryTest {

  @Autowired
  MemberJpaRepository jpaRepository;

  @Test
  void testMember() {
    Member member = Member.builder().userName("memberA").build();

    Member saveMember = jpaRepository.save(member);

    Member findMember = jpaRepository.find(saveMember.getId());

    assertEquals(findMember.getId(), member.getId());
    assertEquals(findMember.getUserName(), member.getUserName());
    assertEquals(findMember, member);
  }

}
