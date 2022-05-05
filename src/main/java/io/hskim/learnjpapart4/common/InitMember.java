package io.hskim.learnjpapart4.common;

import io.hskim.learnjpapart4.entity.Member;
import io.hskim.learnjpapart4.entity.Team;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class InitMember {

  private final InitMemberService initMemberService;

  @PostConstruct
  void init() {
    initMemberService.init();
  }

  @Component
  static class InitMemberService {

    @PersistenceContext
    private EntityManager em;

    @Transactional
    public void init() {
      Team teamA = Team.builder().teamName("teamA").build();
      Team teamB = Team.builder().teamName("teamB").build();

      em.persist(teamA);
      em.persist(teamB);

      for (int i = 0; i < 100; i++) {
        Team selectedTeam = (i % 2) == 0 ? teamA : teamB;

        em.persist(
          Member
            .builder()
            .userName("member%d".formatted(i))
            .age(i)
            .team(selectedTeam)
            .build()
        );
      }
    }
  }
}
