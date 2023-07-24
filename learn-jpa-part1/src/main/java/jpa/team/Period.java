package jpa.team;

import java.time.LocalDateTime;

import javax.persistence.Embeddable;

@Embeddable
public class Period {

  private LocalDateTime startDateTime;
  private LocalDateTime endDateTime;
}
