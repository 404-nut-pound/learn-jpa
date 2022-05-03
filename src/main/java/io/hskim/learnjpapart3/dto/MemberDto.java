package io.hskim.learnjpapart3.dto;

import io.hskim.learnjpapart3.entity.Member;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.ObjectUtils;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

  private Long id;

  private String userName;

  private String teamName;

  public MemberDto(Member member) {
    this.id = member.getId();
    this.userName = member.getUserName();
    this.teamName =
      ObjectUtils.isEmpty(member.getTeam())
        ? ""
        : member.getTeam().getTeamName();
  }
}
