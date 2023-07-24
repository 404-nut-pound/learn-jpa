package io.hskim.learnjpapart2.api;

import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.service.MemberService;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member/api")
@RequiredArgsConstructor
public class MemberApiController {

  private final MemberService memberService;

  @PostMapping
  public CommonMemberResponse postMember(
    @ModelAttribute @Valid CommonMemberResponse commonMemberResponse
  ) {
    Long userId = memberService.insertMember(
      Member.builder().name(commonMemberResponse.getUserName()).build()
    );

    return new CommonMemberResponse(userId);
  }

  // 엔티티를 직접 API 통신에 사용하는 것은 위험 요소가 있음
  // 별도의 통신용 DTO 객체를 만들어서 사용하는 것이 안전함(api 스펙 변경 고정, 테이블 정보 노출 등)
  @Data
  @AllArgsConstructor
  class CommonMemberResponse {

    private Long id;

    private String userName;

    public CommonMemberResponse(Long id) {
      this.id = id;
    }
  }
}
