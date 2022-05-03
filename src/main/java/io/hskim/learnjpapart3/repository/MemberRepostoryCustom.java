package io.hskim.learnjpapart3.repository;

import io.hskim.learnjpapart3.entity.Member;
import java.util.List;

public interface MemberRepostoryCustom {
  List<Member> findMemberCustom();
}
