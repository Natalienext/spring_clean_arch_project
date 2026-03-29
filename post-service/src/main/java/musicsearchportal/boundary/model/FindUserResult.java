package musicsearchportal.boundary.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
public class FindUserResult {

  String userId;
  String displayName;
  Integer yearsExperience;
}
