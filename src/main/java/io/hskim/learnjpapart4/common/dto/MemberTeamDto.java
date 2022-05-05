package io.hskim.learnjpapart4.common.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberTeamDto {

  private Long memberId;
  private String userName;
  private int age;
  private Long teamId;
  private String teamName;

  @QueryProjection
  public MemberTeamDto(
    Long memberId,
    String userName,
    int age,
    Long teamId,
    String teamName
  ) {
    this.memberId = memberId;
    this.userName = userName;
    this.age = age;
    this.teamId = teamId;
    this.teamName = teamName;
  }
}
