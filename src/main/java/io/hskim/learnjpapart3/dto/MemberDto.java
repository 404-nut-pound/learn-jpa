package io.hskim.learnjpapart3.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MemberDto {

  private Long id;

  private String userName;

  private String teamName;

}
