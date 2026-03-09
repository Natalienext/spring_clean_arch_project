package userservice.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

  private final UserId userID;
  private String displayName;
  private Integer yearsExperience;
  private Instant createdAt;
  private UserStatus userStatus;
}
