package io.hskim.learnjpapart2;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart2.member.MemberRepository;
import io.hskim.learnjpapart2.member.vos.Member;

@SpringBootTest
class LearnJpaPart2ApplicationTests {

  @Autowired
  MemberRepository memberRepository;

  @Test
  @Transactional // db 테스트시 필요함
  @Rollback(false) // db 롤백 여부 선택
  void contextLoads() {
    Member member = Member.builder().userName("memberA").build();

    Long userId = memberRepository.save(member);

    Member findMember = memberRepository.find(userId);

    assertTrue(findMember.getId() == (userId));
    assertTrue(findMember.getUserName().equals(member.getUserName()));
    assertTrue(findMember == member); // 동일 트랜잭션이기 때문에 동일성 보장
  }

}
