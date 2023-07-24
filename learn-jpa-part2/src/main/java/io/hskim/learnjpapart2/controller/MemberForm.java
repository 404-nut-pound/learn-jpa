package io.hskim.learnjpapart2.controller;

import javax.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MemberForm {
  @NotBlank(message = "회원 이름은 필수 입니다.")
  private String name;

  private String city;

  private String street;

  private String zipCode;
}
