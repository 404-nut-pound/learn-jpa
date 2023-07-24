package io.hskim.learnjpapart4.common;

import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import io.hskim.learnjpapart4.repository.MemberJpaRepository;
import io.hskim.learnjpapart4.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MemberContorller {

  private final MemberJpaRepository memberJpaRepository;
  private final MemberRepository memberRepository;

  @GetMapping(value = "/v1/member")
  public List<MemberTeamDto> searchMemberV1(MemberSearchCondition condition) {
    return memberJpaRepository.search(condition);
  }

  @GetMapping(value = "/v2/member")
  public Page<MemberTeamDto> searchMemberV2(
    MemberSearchCondition condition,
    Pageable pageable
  ) {
    return memberRepository.searchPageSimple(condition, pageable);
  }

  @GetMapping(value = "/v3/member")
  public Page<MemberTeamDto> searchMemberV3(
    MemberSearchCondition condition,
    Pageable pageable
  ) {
    return memberRepository.searchPageComplex(condition, pageable);
  }
}
