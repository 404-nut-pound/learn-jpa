package io.hskim.learnjpapart3.repository;

import io.hskim.learnjpapart3.entity.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<Team, Long> {}
