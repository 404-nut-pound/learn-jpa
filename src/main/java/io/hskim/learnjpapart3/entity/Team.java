package io.hskim.learnjpapart3.entity;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
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
  private Long id;

  private String teamName;

  @Builder.Default
  @OneToMany(mappedBy = "team")
  private List<Member> memberList = new ArrayList<>();
}
