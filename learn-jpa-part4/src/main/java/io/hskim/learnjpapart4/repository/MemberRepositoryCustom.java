package io.hskim.learnjpapart4.repository;

import io.hskim.learnjpapart4.common.dto.MemberSearchCondition;
import io.hskim.learnjpapart4.common.dto.MemberTeamDto;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MemberRepositoryCustom {
  List<MemberTeamDto> search(MemberSearchCondition condition);
  Page<MemberTeamDto> searchPageSimple(
    MemberSearchCondition condition,
    Pageable pageable
  );
  Page<MemberTeamDto> searchPageComplex(
    MemberSearchCondition condition,
    Pageable pageable
  );
}
