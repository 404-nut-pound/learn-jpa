package io.hskim.learnjpapart4.repository;

import static io.hskim.learnjpapart4.entity.QMember.member;
import static io.hskim.learnjpapart4.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import io.hskim.learnjpapart4.common.dto.QMemberTeamDto;
import io.hskim.learnjpapart4.entity.Member;
import java.util.List;
import javax.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.data.support.PageableExecutionUtils;

public class MemberRepositoryImpl
  extends QuerydslRepositorySupport
  implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  public MemberRepositoryImpl(EntityManager em) {
    super(Member.class);
    this.queryFactory = new JPAQueryFactory(em);
  }

  @Override
  public List<MemberTeamDto> search(MemberSearchCondition condition) {
    return from(member)
      .leftJoin(member.team, team)
      .where(
        userNameEq(condition.getUserName()),
        teamNameEq(condition.getTeamName()),
        ageGoe(condition.getAgeGoe()),
        ageLoe(condition.getAgeLoe())
      )
      .select(
        new QMemberTeamDto(
          member.id,
          member.userName,
          member.age,
          team.id,
          team.teamName
        )
      )
      .fetch();
    // return queryFactory
    //   .select(
    //     new QMemberTeamDto(
    //       member.id.as("memberId"),
    //       member.userName,
    //       member.age,
    //       team.id.as("teamId"),
    //       team.teamName.as("teamName")
    //     )
    //   )
    //   .from(member)
    //   .leftJoin(member.team, team)
    //   .where(
    //     userNameEq(condition.getUserName()),
    //     teamNameEq(condition.getTeamName()),
    //     ageGoe(condition.getAgeGoe()),
    //     ageLoe(condition.getAgeLoe())
    //   )
    //   .fetch();
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

  /**
   * fetchResults가 deprecated되므로 count query를 별도로 생서해서 호출
   */
  @Override
  public Page<MemberTeamDto> searchPageSimple(
    MemberSearchCondition condition,
    Pageable pageable
  ) {
    // List<MemberTeamDto> memberTeamDtoList = queryFactory
    //   .select(
    //     new QMemberTeamDto(
    //       member.id.as("memberId"),
    //       member.userName,
    //       member.age,
    //       team.id.as("teamId"),
    //       team.teamName.as("teamName")
    //     )
    //   )
    //   .from(member)
    //   .leftJoin(member.team, team)
    //   .where(
    //     userNameEq(condition.getUserName()),
    //     teamNameEq(condition.getTeamName()),
    //     ageGoe(condition.getAgeGoe()),
    //     ageLoe(condition.getAgeLoe())
    //   )
    //   .offset(pageable.getOffset())
    //   .limit(pageable.getPageSize())
    //   .fetch();
    JPQLQuery<MemberTeamDto> memberTeamDtoListQuery = from(member)
      .from(member)
      .leftJoin(member.team, team)
      .where(
        userNameEq(condition.getUserName()),
        teamNameEq(condition.getTeamName()),
        ageGoe(condition.getAgeGoe()),
        ageLoe(condition.getAgeLoe())
      )
      .select(
        new QMemberTeamDto(
          member.id.as("memberId"),
          member.userName,
          member.age,
          team.id.as("teamId"),
          team.teamName.as("teamName")
        )
      );

    List<MemberTeamDto> memberTeamDtoList = getQuerydsl()
      .applyPagination(pageable, memberTeamDtoListQuery)
      .fetch();

    return new PageImpl<>(
      memberTeamDtoList,
      pageable,
      memberTeamDtoList.size()
    );
  }

  //QuerydslRepositorySupport 사용시 select가 아니라 from부터 시작해야 함
  @Override
  public Page<MemberTeamDto> searchPageComplex(
    MemberSearchCondition condition,
    Pageable pageable
  ) {
    List<MemberTeamDto> memberTeamDtoList = queryFactory
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
      .offset(pageable.getOffset())
      .limit(pageable.getPageSize())
      .fetch();

    JPAQuery<Long> countQuery = queryFactory
      .select(member.count())
      .from(member)
      // .leftJoin(member.team, team) //leftJoin은 단순 개수 조회일 경우 제외해도 무방함
      .where(
        userNameEq(condition.getUserName()),
        teamNameEq(condition.getTeamName()),
        ageGoe(condition.getAgeGoe()),
        ageLoe(condition.getAgeLoe())
      );

    return PageableExecutionUtils.getPage(
      memberTeamDtoList,
      pageable,
      countQuery::fetchOne
    );
    // return new PageImpl<>(memberTeamDtoList, pageable, total);
  }
}
