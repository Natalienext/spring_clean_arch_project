package musicsearchportal.boundary.model;

import java.time.Instant;
import java.util.List;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CreatePostParam {

  private String description;
  private String author;
  private List<String> hashtags;
  private Instant createdAt;
}
