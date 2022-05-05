package io.hskim.learnjpapart4.common.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberSearchCondition {

  private String userName;

  private String teamName;

  private Integer ageGoe;

  private Integer ageLoe;
}
