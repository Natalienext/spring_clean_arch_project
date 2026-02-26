package musicsearchportal.adapter.repository.post.model;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import lombok.Getter;
import lombok.Setter;
import musicsearchportal.adapter.repository.reply.model.ReplyDbModel;
import musicsearchportal.adapter.repository.shared.model.AuthorInfoDbModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "post")
public class PostDbModel {

  @Id private String id;

  private String title;
  private String description;

  // Встроенные объекты
  private AuthorInfoDbModel author;
  private LocationDbModel location;
  private Set<String> genres;

  private List<ReplyDbModel> replies;

  private String status;
  private String type;

  private Instant createdAt;
  private Instant updatedAt;
  private Instant expiresAt;
}
