package io.hskim.learnjpapart4;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hskim.learnjpapart4.common.dto.CommonDto;
import io.hskim.learnjpapart4.common.dto.QCommonDto;
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
    CommonDto commonDto = new CommonDto();
    em.persist(commonDto);

    JPAQueryFactory query = new JPAQueryFactory(em);

    QCommonDto qCommonDto = new QCommonDto("hello");

    CommonDto result = query.selectFrom(qCommonDto).fetchOne();

    assertEquals(result, commonDto);
    assertEquals(result.getId(), commonDto.getId());
  }
}
