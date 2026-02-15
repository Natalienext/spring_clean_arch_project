package musicsearchportal.adapter.controller.http.repository.reply.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import musicsearchportal.adapter.controller.http.repository.shared.model.AuthorInfoDbModel;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "reply")
@AllArgsConstructor
public class ReplyDbModel {

  private String replyId;
  private String message;
  private Boolean active;

  private AuthorInfoDbModel author;

  private Instant createdAt;
  private Instant updatedAt;
  private Instant viewedAt;
}
