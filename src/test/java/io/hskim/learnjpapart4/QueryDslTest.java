package io.hskim.learnjpapart4;

import static com.querydsl.jpa.JPAExpressions.select;
import static io.hskim.learnjpapart4.entity.QMember.member;
import static io.hskim.learnjpapart4.entity.QTeam.team;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.hskim.learnjpapart4.common.dto.MemberDto;
import io.hskim.learnjpapart4.common.dto.QMemberDto;
import io.hskim.learnjpapart4.common.dto.UserDto;
import io.hskim.learnjpapart4.entity.Member;
import io.hskim.learnjpapart4.entity.QMember;
import io.hskim.learnjpapart4.entity.Team;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional
public class QueryDslTest {

  @Autowired
  EntityManager em;

  private JPAQueryFactory queryFactory;

  @BeforeEach
  void before() {
    queryFactory = new JPAQueryFactory(em);

    Team teamA = Team.builder().teamName("teamA").build();
    Team teamB = Team.builder().teamName("teamB").build();

    em.persist(teamA);
    em.persist(teamB);

    Member memberA = Member
      .builder()
      .userName("memberA")
      .age(10)
      .team(teamA)
      .build();
    Member memberB = Member
      .builder()
      .userName("memberB")
      .age(20)
      .team(teamB)
      .build();
    Member memberC = Member
      .builder()
      .userName("memberC")
      .age(30)
      .team(teamA)
      .build();
    Member memberD = Member
      .builder()
      .userName("memberD")
      .age(40)
      .team(teamB)
      .build();

    em.persist(memberA);
    em.persist(memberB);
    em.persist(memberC);
    em.persist(memberD);
  }

  @Test
  void testJPQL() {
    Member findMember = em
      .createQuery(
        "select m from Member m where m.userName = :userName",
        Member.class
      )
      .setParameter("userName", "memberA")
      .getSingleResult();

    assertEquals(findMember.getUserName(), "memberA");
  }

  @Test
  void testQueryDsl() {
    // QMember m = new QMember("m");
    QMember m = member;

    Member findMember = queryFactory
      // .select(m)
      .select(member)
      // .from(m)
      .from(member)
      .where(m.userName.eq("memberA"))
      .fetchFirst();

    assertEquals(findMember.getUserName(), "memberA");
  }

  @Test
  void search() {
    Member findMember = queryFactory
      .selectFrom(member)
      .where(
        // QMember.member.userName.eq("memberA").and(QMember.member.age.eq(20))
        member.userName.eq("memberA"),
        member.age.eq(10)
      )
      .fetchOne();

    assertEquals(findMember.getUserName(), "memberA");
  }

  @Test
  void resultsFetch() {
    //쿼리가 복잡할 때 count 쿼리의 정확도가 낮아서 아래 내용은 deprecated 예정
    //페이징 쿼리 포함
    QueryResults<Member> results = queryFactory
      .selectFrom(member)
      .fetchResults();

    System.out.println("fetch results - %s".formatted(results));

    //카운트 쿼리만 실행
    long count = queryFactory.selectFrom(member).fetchCount();

    System.out.println("fetch count - %d".formatted(count));
  }

  @Test
  void sort() {
    em.persist(Member.builder().userName(null).age(5).build());
    em.persist(Member.builder().userName("memberE").age(5).build());
    em.persist(Member.builder().userName("memberF").age(5).build());

    List<Member> memberList = queryFactory
      .selectFrom(member)
      .where(member.age.eq(5))
      .orderBy(member.age.desc(), member.userName.asc().nullsLast())
      .fetch();

    Member memberFirst = memberList.get(0);
    Member memberSecond = memberList.get(1);
    Member memberNull = memberList.get(2);

    assertEquals(memberFirst.getUserName(), "memberE");
    assertEquals(memberSecond.getUserName(), "memberF");
    assertEquals(memberNull.getUserName(), null);
  }

  @Test
  void paging() {
    List<Member> result = queryFactory
      .selectFrom(member)
      .orderBy(member.userName.desc())
      .offset(1)
      .limit(2)
      .fetch();

    assertEquals(result.size(), 2);
  }

  @Test
  void aggregation() {
    Tuple tuple = queryFactory
      .select(
        member.count(),
        member.age.sum(),
        member.age.avg(),
        member.age.max(),
        member.age.min()
      )
      .from(member)
      .fetchOne();

    assertEquals(tuple.get(member.count()), 4);
    assertEquals(tuple.get(member.age.sum()), 100);
    assertEquals(tuple.get(member.age.avg()), 25);
    assertEquals(tuple.get(member.age.max()), 40);
    assertEquals(tuple.get(member.age.min()), 10);
  }

  @Test
  void group() {
    List<Tuple> tupleList = queryFactory
      .select(team.teamName, member.age.avg())
      .from(member)
      .join(member.team, team)
      .groupBy(team.teamName)
      .fetch();

    Tuple teamA = tupleList.get(0);
    Tuple teamB = tupleList.get(1);

    assertEquals(teamA.get(team.teamName), "teamA");
    assertEquals(teamA.get(member.age.avg()), 20);

    assertEquals(teamB.get(team.teamName), "teamB");
    assertEquals(teamB.get(member.age.avg()), 30);
  }

  @Test
  void join() {
    List<Member> memberList = queryFactory
      .selectFrom(member)
      // .join(member.team, team)
      .leftJoin(member.team, team)
      .where(team.teamName.eq("teamA"))
      .fetch();

    assertEquals(
      memberList
        .stream()
        .filter(member -> "teamA".equals(member.getTeam().getTeamName()))
        .collect(Collectors.toList())
        .size(),
      2
    );
  }

  @Test
  void thetaJoin() {
    em.persist(Member.builder().userName("teamA").build());
    em.persist(Member.builder().userName("teamB").build());

    List<Member> memberList = queryFactory
      .select(member)
      .from(member, team)
      .where(member.userName.eq(team.teamName))
      .fetch();

    assertEquals(
      memberList
        .stream()
        .filter(member -> member.getUserName().startsWith("team"))
        .collect(Collectors.toList())
        .size(),
      2
    );
  }

  @Test
  void joinOnFiltering() {
    List<Tuple> tupleList = queryFactory
      .select(member, team)
      .from(member)
      .leftJoin(member.team, team)
      .on(team.teamName.eq("teamA"))
      .fetch();

    tupleList.stream().forEach(System.out::println);
  }

  @Test
  void joinOnNoRelation() {
    em.persist(Member.builder().userName("teamA").build());
    em.persist(Member.builder().userName("teamB").build());

    List<Tuple> tupleList = queryFactory
      .select(member, team)
      .from(member)
      // .leftJoin(member.team, team) //id로 join 처리
      .leftJoin(team)
      .on(member.userName.eq(team.teamName))
      .where(member.userName.eq(team.teamName))
      .fetch();

    tupleList.stream().forEach(System.out::println);
  }

  @PersistenceUnit
  EntityManagerFactory emf;

  @Test
  void fetchJoin() {
    em.flush();
    em.clear();

    Member findMember = queryFactory
      .selectFrom(member)
      .where(member.userName.eq("memberA"))
      .fetchOne();

    boolean isLoaded = emf
      .getPersistenceUnitUtil()
      .isLoaded(findMember.getTeam());

    assertFalse(isLoaded);

    findMember =
      queryFactory
        .selectFrom(member)
        .join(member.team, team)
        .fetchJoin()
        .where(member.userName.eq("memberA"))
        .fetchOne();

    isLoaded = emf.getPersistenceUnitUtil().isLoaded(findMember.getTeam());

    assertTrue(isLoaded);
  }

  @Test
  void subQuery() {
    QMember subMember = new QMember("subMember");

    List<Member> memberList = queryFactory
      .selectFrom(member)
      .where(member.age.eq(select(subMember.age.max()).from(subMember)))
      .fetch();

    assertEquals(memberList.get(0).getAge(), 40);
  }

  @Test
  void selectSubQuery() {
    QMember subMember = new QMember("subMember");

    List<Tuple> memberList = queryFactory
      .select(member.userName, select(subMember.age.avg()).from(subMember))
      .from(member)
      .fetch();

    memberList.stream().forEach(System.out::println);
  }

  @Test
  void basicCase() {
    List<String> memberList = queryFactory
      .select(
        member.age
          .when(10)
          .then("10 살")
          .when(20)
          .then("20 살")
          .otherwise("기타")
      )
      .from(member)
      .fetch();

    memberList.stream().forEach(System.out::println);
  }

  @Test
  void complexCase() {
    List<String> memberList = queryFactory
      .select(
        new CaseBuilder()
          .when(member.age.between(0, 20))
          .then("0~20 살")
          .when(member.age.between(21, 30))
          .then("21~30 살")
          .otherwise("기타")
      )
      .from(member)
      .fetch();

    memberList.stream().forEach(System.out::println);
  }

  @Test
  void concat() {
    List<Tuple> tupleList = queryFactory
      .select(member.userName, Expressions.constant("ABCD"))
      .from(member)
      .fetch();

    tupleList.stream().forEach(System.out::println);

    List<String> concatList = queryFactory
      .select(member.userName.concat("_").concat(member.age.stringValue()))
      .from(member)
      .fetch();

    concatList.stream().forEach(System.out::println);
  }

  @Test
  void simpleProjection() {
    List<String> userNameList = queryFactory
      .select(member.userName)
      .from(member)
      .fetch();

    userNameList.stream().forEach(System.out::println);
  }

  @Test
  void tupleProjection() {
    List<Tuple> tupleList = queryFactory
      .select(member.userName, member.age)
      .from(member)
      .fetch();

    tupleList
      .stream()
      .forEach(
        tuple -> {
          System.out.println(
            "userName - %s\nage - %d".formatted(
                tuple.get(member.userName),
                tuple.get(member.age)
              )
          );
        }
      );
  }

  @Test
  void findDtoByJPQL() {
    List<MemberDto> memberDtoList = em
      .createQuery(
        "select new io.hskim.learnjpapart4.common.dto.MemberDto(m.userName, m.age) from Member m",
        MemberDto.class
      )
      .getResultList();

    memberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void findDtoBySetter() {
    List<MemberDto> memberDtoList = queryFactory
      .select(Projections.bean(MemberDto.class, member.userName, member.age))
      .from(member)
      .fetch();

    memberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void findDtoByField() {
    List<MemberDto> memberDtoList = queryFactory
      .select(Projections.fields(MemberDto.class, member.userName, member.age))
      .from(member)
      .fetch();

    memberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void findDtoByConstructor() {
    List<MemberDto> memberDtoList = queryFactory
      .select(
        Projections.constructor(MemberDto.class, member.userName, member.age)
      )
      .from(member)
      .fetch();

    memberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void findUserDto() {
    QMember subMember = new QMember("subMember");

    List<UserDto> memberDtoList = queryFactory
      .select(
        Projections.fields(
          UserDto.class,
          member.userName.as("name"),
          ExpressionUtils.as(select(subMember.age.max()).from(subMember), "age")
        )
      )
      .from(member)
      .fetch();

    memberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void findDtoByQueryProjection() {
    List<MemberDto> qMemberDtoList = queryFactory
      .select(new QMemberDto(member.userName, member.age))
      .from(member)
      .fetch();

    qMemberDtoList.stream().forEach(System.out::println);
  }

  @Test
  void distinct() {
    List<Member> memberList = queryFactory
      .select(member)
      .distinct()
      .from(member)
      .fetch();

    memberList.stream().forEach(System.out::println);
  }

  @Test
  void dynamicQueryByBooleanBuilder() {
    String userNameParam = "memberA";
    Integer ageParam = 10;

    List<Member> memberList = searchMember1(userNameParam, ageParam);

    assertEquals(memberList.size(), 1);
  }

  private List<Member> searchMember1(String userNameParam, Integer ageParam) {
    BooleanBuilder booleanBuilder = new BooleanBuilder(/* 초기값 입력 가능 */);

    if (userNameParam != null) {
      booleanBuilder.and(member.userName.eq(userNameParam));
    }
    if (ageParam != null) {
      booleanBuilder.and(member.age.eq(ageParam));
    }

    return queryFactory.selectFrom(member).where(booleanBuilder).fetch();
  }

  @Test
  void dynamicQueryByWhereParam() {
    String userNameParam = "memberA";
    Integer ageParam = null;

    List<Member> memberList = searchMember2(userNameParam, ageParam);

    assertEquals(memberList.size(), 1);
  }

  private List<Member> searchMember2(String userNameParam, Integer ageParam) {
    return queryFactory
      .selectFrom(member)
      .where(isBothEquals(userNameParam, ageParam))
      .fetch();
  }

  private BooleanExpression userNameEq(String userNameParam) {
    return userNameParam == null ? null : member.userName.eq(userNameParam);
  }

  private BooleanExpression ageEq(Integer ageParam) {
    return ageParam == null ? null : member.age.eq(ageParam);
  }

  private BooleanExpression isBothEquals(
    String userNameParam,
    Integer ageParam
  ) {
    return userNameEq(userNameParam).and(ageEq(ageParam));
  }

  @Test
  void bulkUpdate() {
    // 예상 - 2명은 비회원으로 업데이트
    // 하지만 영속성 컨텍스트가 우선권을 가지므로 flush/clear를 하지 않으면 select 결과가 기존 값 유지
    long updateCount = queryFactory
      .update(member)
      .set(member.userName, "비회원")
      .where(member.age.lt(28))
      .execute();

    List<Member> fetchBefore = queryFactory.selectFrom(member).fetch();

    fetchBefore.stream().forEach(System.out::println);

    em.flush();
    em.clear();

    List<Member> fetchAfter = queryFactory.selectFrom(member).fetch();

    fetchAfter.stream().forEach(System.out::println);
  }

  @Test
  void bulkAdd() {
    long updateCount = queryFactory
      .update(member)
      .set(member.age, member.age.add(1))
      .execute();
  }

  @Test
  void bulkDelete() {
    long deleteCount = queryFactory
      .delete(member)
      .where(member.age.gt(18))
      .execute();
  }

  @Test
  void sqlFunction() {
    List<String> stringList = queryFactory
      .select(
        Expressions.stringTemplate(
          "function('replace', {0}, {1}, {2})",
          member.userName,
          "member",
          "M"
        )
      )
      .from(member)
      .fetch();

    stringList.stream().forEach(System.out::println);
  }

  @Test
  void sqlFunction2() {
    List<String> stringList = queryFactory
      .select(member.userName)
      .from(member)
      // .where(
      //   member.userName.eq(
      //     Expressions.stringTemplate("function('lower', {0})", member.userName)
      //   )
      // )
      .where(member.userName.eq(member.userName.lower()))
      .fetch();

    stringList.stream().forEach(System.out::println);
  }
}
