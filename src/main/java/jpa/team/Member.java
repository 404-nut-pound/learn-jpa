package jpa.team;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Data;

@Entity
@Data
public class Member {

  @Id
  @GeneratedValue
  @Column(name = "memeber_id")
  private Long id;
  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;
  @Column(name = "user_name")
  private String userName;
}
