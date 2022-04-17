package jpa.team;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Team {

  @Id
  @GeneratedValue
  @Column(name = "team_id")
  private Long id;
  private String name;
}
