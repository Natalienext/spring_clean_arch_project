package musicsearchportal.boundary.model;

import java.time.Instant;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class AddReplyParam {
  private UUID postId;
  private String message;
  private UUID authorId;
  private String authorName;
  private int authorYearsExperience;
  private Instant createdAt;
}
