package io.hskim.learnjpapart3.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart3.entity.Member;

@SpringBootTest
@Transactional
public class MemberRepositoryTest {

  @Autowired
  MemberRepository repository;

  @Test
  void testMember() {
    Member member = Member.builder().userName("memberA").build();

    Member saveMember = repository.save(member);

    Optional<Member> findMember = repository.findById(saveMember.getId());

    assertEquals(findMember.get().getId(), member.getId());
    assertEquals(findMember.get().getUserName(), member.getUserName());
    assertEquals(findMember.get(), member);
  }
}
