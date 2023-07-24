package io.hskim.learnjpapart2.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.repository.MemberRepositoryOld;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Transactional // 테스트 코드이므로 자동 롤백 설정
@ActiveProfiles("test")
public class MemberServiceTest {

  @Autowired
  MemberService memberService;

  @Autowired
  MemberRepositoryOld memberRepository;

  @Autowired
  EntityManager entityManager;

  @Test
  void testInsertMember() {
    Member member = Member.builder().name("memberA").build();

    Long savedId = memberService.insertMember(member);

    // 자동 롤백 설정이 돼있지만 insert 쿼리 발생
    entityManager.flush();
    assertEquals(member, memberService.selectMemberById(savedId));
  }

  @Test
  void testMemberDuplecated() {
    Member memberA = Member.builder().name("member").build();
    Member memberB = Member.builder().name("member").build();

    memberService.insertMember(memberA);
    // try {
    // memberService.insertMember(memberB); // 예외 발생 예상
    // } catch (IllegalStateException e) {
    // return;
    // }
    IllegalStateException thrownException = assertThrows(
      IllegalStateException.class,
      () -> memberService.insertMember(memberB)
    );
    assertEquals("이미 존재하는 회원입니다.", thrownException.getMessage());
    // fail("중복 테스트 실패");
  }
}
