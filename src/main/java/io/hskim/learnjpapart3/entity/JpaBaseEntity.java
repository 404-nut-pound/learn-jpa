package io.hskim.learnjpapart3.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;

@MappedSuperclass
public class JpaBaseEntity {

  @Column(updatable = false)
  private LocalDateTime regDt;

  private LocalDateTime modDt;

  //insert 전처리
  @PrePersist
  public void PrePersist() {
    LocalDateTime now = LocalDateTime.now();

    this.regDt = now;
    this.modDt = now;
  }

  //update 전처리
  @PreUpdate
  public void preUpdate() {
    LocalDateTime now = LocalDateTime.now();

    this.modDt = now;
  }
}
