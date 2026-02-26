package musicsearchportal.adapter.repository.reply.model;

import java.time.Instant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import musicsearchportal.adapter.repository.shared.model.AuthorInfoDbModel;

@Getter
@Setter
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
