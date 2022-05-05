package io.hskim.learnjpapart4;

import com.querydsl.jpa.impl.JPAQueryFactory;
import javax.persistence.EntityManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LearnJpaPart4Application {

  public static void main(String[] args) {
    SpringApplication.run(LearnJpaPart4Application.class, args);
  }

  //Spring Bean으로 등록해서 다른 repo에서 호출해서 사용
  @Bean
  JPAQueryFactory jpaQueryFactory(EntityManager em) {
    return new JPAQueryFactory(em);
  }
}
