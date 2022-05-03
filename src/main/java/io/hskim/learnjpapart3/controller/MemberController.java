package io.hskim.learnjpapart3.controller;

import io.hskim.learnjpapart3.entity.Member;
import io.hskim.learnjpapart3.repository.MemberRepository;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
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
    memberRepository.save(Member.builder().userName("memberA").age(20).build());
  }

  @GetMapping(value = "/v1/{id}")
  public String getV1Member(@PathVariable Long id) {
    return memberRepository.findById(id).get().getUserName();
  }

  @GetMapping(value = "/v2/{id}")
  public String getV2Member(@PathVariable(name = "id") Member member) {
    return memberRepository.findById(member.getId()).get().getUserName();
  }
}
