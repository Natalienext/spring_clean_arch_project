package userservice.domain.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Data;
import userservice.boundary.model.ChangeUserParam;

@AllArgsConstructor
@Data
public class User {

  private final UserId userID;
  private String displayName;
  private Integer yearsExperience;
  private Instant createdAt;
  private UserStatus userStatus;

  public boolean merge(ChangeUserParam param) {
    boolean changed = false;

    if (param.displayName() != null && !param.displayName().equals(this.displayName)) {
      this.displayName = param.displayName();
      changed = true;
    }
    if (param.yearsExperience() != null && !param.yearsExperience().equals(this.yearsExperience)) {
      this.yearsExperience = param.yearsExperience();
      changed = true;
    }
    if (param.userStatus() != null) {
      this.userStatus = param.userStatus();
    }

    return changed;
  }
}
