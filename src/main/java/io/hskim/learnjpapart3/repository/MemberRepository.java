package io.hskim.learnjpapart3.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import io.hskim.learnjpapart3.entity.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

}
