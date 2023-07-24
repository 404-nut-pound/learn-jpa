package io.hskim.learnjpapart2.domain;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Embedded;
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
import lombok.Setter;

@Entity
@Data
@Setter(value = AccessLevel.NONE)
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  private String name;

  @Embedded
  private Address Address;

  @Builder.Default
  @OneToMany(mappedBy = "member", fetch = FetchType.LAZY) // 양방향 매핑의 경우 연관 관계의 주도권이 없는 쪽에 mappedBy 설정
  private List<Order> orderList = new ArrayList<>();
}
