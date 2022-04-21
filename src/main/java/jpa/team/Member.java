package jpa.team;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
//@Data
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
  @Embedded
  @AttributeOverrides({ @AttributeOverride(name = "city", column = @Column(name = "work_city")),
      @AttributeOverride(name = "street", column = @Column(name = "work_street")),
      @AttributeOverride(name = "zipcode", column = @Column(name = "work_zipcode")), })
  private Address address;
  private String city;
  private String street;
  private String zipcode;
  @Embedded
  @AttributeOverride(name = "startDateTime", column = @Column(name = "work_start_datetime"))
  @AttributeOverride(name = "endDateTime", column = @Column(name = "work_end_datetime"))
  private Period period;

  public void setTeam(Team team) {
    this.team = team;
    team.getMemberList().add(this);
  }
}
