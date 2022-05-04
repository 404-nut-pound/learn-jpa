package io.hskim.learnjpapart4;

import javax.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
class LearnJpaPart4ApplicationTests {

  @Autowired
  EntityManager em;

  @Test
  void contextLoads() {
    // MemberDto commonDto = new MemberDto();
    // em.persist(commonDto);

    // JPAQueryFactory query = new JPAQueryFactory(em);

    // QCommonDto qCommonDto = new QCommonDto("hello");

    // MemberDto result = query.selectFrom(qCommonDto).fetchOne();

    // assertEquals(result, commonDto);
    // assertEquals(result.getId(), commonDto.getId());
  }
}
