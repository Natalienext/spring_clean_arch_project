package musicsearchportal.boundary.model;

import java.util.Set;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePostParam {

  private String title;
  private String description;
  private UUID authorId;
  private String authorName;
  private int authorYearsExperience;
  private String city;
  private String district;
  private Boolean remoteOk;
  private Set<String> genres;
  private String postType;
}
