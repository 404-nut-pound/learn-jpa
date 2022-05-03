package io.hskim.learnjpapart3.controller;

import io.hskim.learnjpapart3.dto.MemberDto;
import io.hskim.learnjpapart3.entity.Member;
import io.hskim.learnjpapart3.repository.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/member")
@RequiredArgsConstructor
public class MemberController {

  private final MemberRepository memberRepository;

  @PostConstruct
  public void init() {
    // memberRepository.save(Member.builder().userName("memberA").age(20).build());
    for (int i = 0; i < 100; i++) {
      memberRepository.save(
        Member.builder().userName("member%d".formatted(i)).build()
      );
    }
  }

  @GetMapping(value = "/v1/{id}")
  public String getV1Member(@PathVariable Long id) {
    return memberRepository.findById(id).get().getUserName();
  }

  @GetMapping(value = "/v2/{id}")
  public String getV2Member(@PathVariable(name = "id") Member member) {
    return memberRepository.findById(member.getId()).get().getUserName();
  }

  @GetMapping(value = "/page")
  public Page<MemberDto> getMemberPage(
    // @Qualifier(value = "member") //파라미터에 대한 접두사 설정, 파라미터 클래스가 여러가지일 때 사용
    @PageableDefault(size = 5) Pageable pageable
  ) {
    Page<Member> pagedMember = memberRepository.findAll(pageable); //엔티티로 반환 금지!!!!!!!

    Page<MemberDto> pagedMemberDto = pagedMember.map(
      // member -> new MemberDto(member.getId(), member.getUserName(), null)
      MemberDto::new
    );

    return pagedMemberDto;
  }
}
