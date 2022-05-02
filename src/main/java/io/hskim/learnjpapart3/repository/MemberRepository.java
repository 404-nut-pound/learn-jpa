package io.hskim.learnjpapart3.repository;

import java.util.List;
import java.util.Optional;

import javax.persistence.LockModeType;
import javax.persistence.QueryHint;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;

import io.hskim.learnjpapart3.dto.MemberDto;
import io.hskim.learnjpapart3.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
  public List<Member> findByUserNameAndAgeGreaterThan(String userName, int age);

  /**
   * 스프링 Data JPA에서 네임드 쿼리 호출
   * 메소드 명과 엔티티의 네임드 쿼리가 일치하는 것이 있거나 메소드 자동 생성이 가능할 때 @Query 생략 가능
   * JPQL 직접 정의도 가능
   * 네임드 쿼리는 컴파일 시점에서 오류 검출이 가능
   *
   * @param userName
   * @return
   */
  // @Query(name = "Member.findByUserName")
  @Query("select m from Member m where m.userName = :userName")
  public List<Member> findByUserName(@Param("userName") String userName);

  @Query("select m.userName from Member m")
  List<String> findUserNameList();

  @Query("select new io.hskim.learnjpapart3.dto.MemberDto(m.id, m.userName, t.teamName) from Member m join m.team t")
  List<MemberDto> findMemberDto();

  @Query("select m from Member m where m.userName in :names")
  List<Member> findByNames(@Param("names") List<String> names);

  Member findMemberByUserName(String userName);

  Optional<Member> findOptinalMemberByUserName(String userName);

  /**
   * 자동 페이징 쿼리 생성
   * 조건이 1개 이상 있어야 함
   * Slice 리턴은 totalCount를 계산하지 않고 limit + 1개 조회로 '더 보기'를 생성함
   * 데이터 쿼리와 카운트 쿼리를 분리 가능
   *
   * @param pageable
   * @param age
   * @return
   */
  @Query(value = "select m from Member m", countQuery = "select count(m) from Member m")
  Page<Member> findPagedMemberByAge(Pageable pageable, int age);

  /**
   * Modifying 어노테이션을 사용해야 executeUpdate를 실행함
   * 벌크 연산을 사용하면 영속성 컨텍스트를 초기화(clear()) 권장 혹은 어노테이션 옵션 사용
   *
   * @param age
   * @return
   */
  @Modifying(clearAutomatically = true)
  @Query("update Member m set m.age = m.age + :age")
  int bulkAgePlus(@Param("age") int age);

  /**
   * EntityGraph - 지정한 속성에 대해서 fetch join 실행
   * 엔티티에서 NamedEntityGraph 설정 후 사용 가능
   */
  @Override
  // @EntityGraph(attributePaths = { "team" })
  @EntityGraph("Member.all")
  public List<Member> findAll();

  /**
   * Hibernate에서 제공하는 힌트 기능으로 쿼리 옵션 설정 가능
   * org.hibernate.readOnly - 읽기 전용으로 조회, 변경 감지 적용 안 됨
   * 
   * @param userName
   * @return
   */
  @QueryHints(value = { @QueryHint(name = "org.hibernate.readOnly", value = "true") })
  Member findReadOnlyMemberByUserName(String userName);

  /**
   * Lock 어노테이션으로 쿼리 옵션 추가 가능
   * 
   * @param userName
   * @return
   */
  @Lock(LockModeType.PESSIMISTIC_READ)
  List<Member> findLockedMemberByUserName(String userName);

}
