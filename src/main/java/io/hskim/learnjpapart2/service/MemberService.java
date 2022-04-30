package io.hskim.learnjpapart2.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.hskim.learnjpapart2.domain.Member;
import io.hskim.learnjpapart2.repository.MemberRepository;
import lombok.RequiredArgsConstructor;

@Service
// 조회성 로직의 경우 readOnly를 설정하면 좀 더 최적화 가능
@Transactional(readOnly = true)
@RequiredArgsConstructor // 필수적인 필드에 대해 생성자 자동 생성
public class MemberService {

  // setter injection
  // public void setMemberRepository(MemberRepository memberRepository) {
  // this.memberRepository = memberRepository;
  // }
  // constructor injection
  // @Autowired // 생략 가능
  // public MemberService(MemberRepository memberRepository) {
  // this.memberRepository = memberRepository;
  // }
  // @Autowired
  private final MemberRepository memberRepository;

  @Transactional
  public Long insertMember(Member member) {
    validateDupleMember(member);

    memberRepository.insertMember(member);

    return member.getId();
  }

  private void validateDupleMember(Member member) {
    List<Member> memberList = memberRepository.findByName(member.getName());

    if (!memberList.isEmpty()) {
      throw new IllegalStateException("이미 존재하는 회원입니다.");
    }
  }

  public List<Member> selectMemberAll() {
    return memberRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Member selectMemberById(Long id) {
    return memberRepository.findOne(id);
  }
}
