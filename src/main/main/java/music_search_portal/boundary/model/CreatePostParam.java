package music_search_portal.boundary.model;

import java.time.Instant;
import java.util.List;
import lombok.Builder;

@Builder
public class CreatePostParam {

  String description;
  String author;
  List<String> hashtags;
  Instant createdAt;
}
