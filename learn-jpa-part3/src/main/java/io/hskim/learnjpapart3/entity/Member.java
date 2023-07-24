package io.hskim.learnjpapart3.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQuery;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.domain.Persistable;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = { "id", "userName", "age" })
@EqualsAndHashCode(callSuper = false)
@NamedQuery(
  name = "findByUserName",
  query = "select m from Member m where m.userName = :userName"
)
@NamedEntityGraph(
  name = "Member.all",
  attributeNodes = @NamedAttributeNode(value = "team")
)
public class Member extends BaseEntity implements Persistable<Long> {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String userName;

  private int age;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "team_id")
  private Team team;

  public void changeTeam(Team team) {
    this.team = team;
    team.getMemberList().add(this);
  }

  //일반 repository의 save를 사용할 때 merge로 작동하면 성능이 저하됨(select 과정 추가됨)
  //Persistable 인터페이스를 구현하여 해당 객체가 isNew 상태인지 처리하면 좀 더 나은 성능을 기대할 수 있음
  @Override
  public boolean isNew() {
    return super.getRegDt() == null;
  }
}
