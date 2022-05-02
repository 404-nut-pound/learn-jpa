package io.hskim.learnjpapart3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.hskim.learnjpapart3.entity.Team;

public interface TeamRepository extends JpaRepository<Team, Long> {

}
