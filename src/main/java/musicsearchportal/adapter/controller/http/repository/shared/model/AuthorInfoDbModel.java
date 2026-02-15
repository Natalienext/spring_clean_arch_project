package musicsearchportal.adapter.controller.http.repository.shared.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class AuthorInfoDbModel {
  private String userId;
  private String displayName;
  private Integer yearsExperience;
}
