package jpa.shop;

import java.time.LocalDateTime;

import javax.persistence.MappedSuperclass;

import lombok.Data;

@MappedSuperclass // 공통 요소를 정의하는 엔티티
@Data
public abstract class BaseEntity {

  private String regId;
  private LocalDateTime regDt;
  private String modId;
  private LocalDateTime modDt;
}
