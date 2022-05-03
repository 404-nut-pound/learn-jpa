package io.hskim.learnjpapart3;

import java.util.Optional;
import java.util.UUID;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing //(modifyOnCreate = false) insert 작업에서 lastModified~에 대한 정보가 null로 처리됨
public class LearnJpaPart3Application {

  public static void main(String[] args) {
    SpringApplication.run(LearnJpaPart3Application.class, args);
  }

  /**
   * AuditingEntityListener에게 호출 될 때 지정한 값을 리턴함
   * @return
   */
  @Bean
  public AuditorAware<String> auditorAware() {
    return () -> Optional.of(UUID.randomUUID().toString());
  }
}
