package musicsearchportal.adapter.controller.http.repository.reply.converter;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.controller.http.repository.reply.model.ReplyDbModel;
import musicsearchportal.adapter.controller.http.repository.shared.converter.AuthorInfoConverter;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReplyConverter {

  private final AuthorInfoConverter authorConverter;

  public ReplyDbModel toDbModel(Reply domain) {
    if (domain == null) return null;

    return new ReplyDbModel(
        domain.getReplyId().toString(),
        domain.getMessage(),
        domain.isActive(),
        authorConverter.toDbModel(domain.getAuthor()),
        domain.getCreatedAt().toInstant(ZoneOffset.UTC),
        domain.getUpdatedAt().toInstant(ZoneOffset.UTC),
        domain.getViewedAt() != null ? domain.getViewedAt().toInstant(ZoneOffset.UTC) : null);
  }

  public Reply toEntity(ReplyDbModel dbModel, UUID postId) {
    if (dbModel == null) return null;

    return Reply.from(
        UUID.fromString(dbModel.getReplyId()),
        postId,
        authorConverter.toEntity(dbModel.getAuthor()),
        dbModel.getMessage(),
        dbModel.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        dbModel.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }
}
