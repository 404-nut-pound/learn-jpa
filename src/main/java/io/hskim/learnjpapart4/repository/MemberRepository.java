package io.hskim.learnjpapart4.repository;

import io.hskim.learnjpapart4.entity.Member;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

public interface MemberRepository
  extends
    JpaRepository<Member, Long>,
    MemberRepositoryCustom,
    QuerydslPredicateExecutor<Member> {
  List<Member> findByUserName(String userName);
}
