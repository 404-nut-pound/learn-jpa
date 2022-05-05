package io.hskim.learnjpapart4.repository;

import static io.hskim.learnjpapart4.entity.QMember.member;
import static io.hskim.learnjpapart4.entity.QTeam.team;
import static org.springframework.util.StringUtils.hasText;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import io.hskim.learnjpapart4.common.dto.QMemberTeamDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {

  private final JPAQueryFactory queryFactory;

  @Override
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

  /**
   * fetchResults가 deprecated되므로 count query를 별도로 생서해서 호출
   */
  @Override
  public Page<MemberTeamDto> searchPageSimple(
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

    return new PageImpl<>(
      memberTeamDtoList,
      pageable,
      memberTeamDtoList.size()
    );
  }

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
