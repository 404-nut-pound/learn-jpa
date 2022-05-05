package io.hskim.learnjpapart4.repository;

import static io.hskim.learnjpapart4.entity.QMember.member;
import static io.hskim.learnjpapart4.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import io.hskim.learnjpapart4.common.dto.QMemberTeamDto;
import io.hskim.learnjpapart4.entity.Member;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
public class MemberJpaRepository {

  private final EntityManager em;
  private final JPAQueryFactory queryFactory;

  public MemberJpaRepository(
    EntityManager em,
    JPAQueryFactory jpaQueryFactory
  ) {
    this.em = em;
    this.queryFactory = jpaQueryFactory;
  }

  public void save(Member member) {
    em.persist(member);
  }

  public Optional<Member> findById(Long id) {
    Member findMember = em.find(Member.class, id);

    return Optional.ofNullable(findMember);
  }

  public List<Member> findByUserName(String userName) {
    return em
      .createQuery(
        "select m from Member m where m.userName = :userName",
        Member.class
      )
      .setParameter("userName", userName)
      .getResultList();
  }

  public List<Member> findByUserNameQueryDsl(String userName) {
    return queryFactory
      .selectFrom(member)
      .where(member.userName.eq(userName))
      .fetch();
  }

  public List<MemberTeamDto> searchByBuilder(MemberSearchCondition condition) {
    BooleanBuilder builder = new BooleanBuilder();

    if (hasText(condition.getUserName())) {
      builder.and(member.userName.eq(condition.getUserName()));
    }
    if (hasText(condition.getTeamName())) {
      builder.and(team.teamName.eq(condition.getTeamName()));
    }
    if (condition.getAgeGoe() != null) {
      builder.and(member.age.goe(condition.getAgeGoe()));
    }
    if (condition.getAgeLoe() != null) {
      builder.and(member.age.loe(condition.getAgeLoe()));
    }

    return queryFactory
      .select(
        new QMemberTeamDto(
          member.id.as("memberId"),
          member.userName,
          member.age,
          team.id.as("teamId"),
          team.teamName.as("teamName")
        )
      )
      .from(member)
      .leftJoin(member.team, team)
      .where(builder)
      .fetch();
  }

  public List<MemberTeamDto> search(MemberSearchCondition condition) {
    return queryFactory
      .select(
        new QMemberTeamDto(
          member.id.as("memberId"),
          member.userName,
          member.age,
          team.id.as("teamId"),
          team.teamName.as("teamName")
        )
      )
      .from(member)
      .leftJoin(member.team, team)
      .where(
        userNameEq(condition.getUserName()),
        teamNameEq(condition.getTeamName()),
        ageGoe(condition.getAgeGoe()),
        ageLoe(condition.getAgeLoe())
      )
      .fetch();
  }

  private BooleanExpression userNameEq(String userName) {
    return hasText(userName) ? member.userName.eq(userName) : null;
  }

  private BooleanExpression teamNameEq(String teamName) {
    return hasText(teamName) ? team.teamName.eq(teamName) : null;
  }

  private BooleanExpression ageGoe(Integer ageGoe) {
    return ageGoe != null ? member.age.goe(ageGoe) : null;
  }

  private BooleanExpression ageLoe(Integer ageLoe) {
    return ageLoe != null ? member.age.loe(ageLoe) : null;
  }
}
