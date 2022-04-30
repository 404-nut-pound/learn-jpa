package io.hskim.learnjpapart2.controller;

import io.hskim.learnjpapart2.domain.Address;
import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.service.MemberService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "/members")
@RequiredArgsConstructor
public class MemberController {
  private final MemberService memberService;

  @GetMapping(value = "/new")
  public String getCreateUserForm(Model model) {
    model.addAttribute("memberForm", MemberForm.builder().build());

    return "members/createMemberForm";
  }

  @PostMapping(value = "/new")
  public String postUserCreateForm(
    @Valid MemberForm memberForm,
    BindingResult bindingResult
  ) {
    if (bindingResult.hasErrors()) {
      return "members/createMemberForm";
    }

    Address address = Address
      .builder()
      .city(memberForm.getCity())
      .street(memberForm.getStreet())
      .zipCode(memberForm.getZipCode())
      .build();

    memberService.insertMember(
      Member.builder().name(memberForm.getName()).Address(address).build()
    );

    return "redirect:/";
  }

  @GetMapping
  public String getMemberList(Model model) {
    model.addAttribute("memberList", memberService.selectMemberAll());
    return "members/memberList";
  }
}
