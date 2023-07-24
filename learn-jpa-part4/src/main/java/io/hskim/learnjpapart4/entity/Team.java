package io.hskim.learnjpapart4.entity;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = { "id", "teamName" })
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "team_id")
  private Long id;

  private String teamName;

  @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
  private List<Member> memberList;

  public List<Member> getMemberList() {
    return this.memberList;
  }
}
