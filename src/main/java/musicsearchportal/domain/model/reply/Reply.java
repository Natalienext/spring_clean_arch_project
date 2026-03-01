package musicsearchportal.domain.model.reply;

import com.github.f4b6a3.uuid.UuidCreator;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import musicsearchportal.domain.exception.DomainException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.PostId;

@Getter
public class Reply {

  // Индентификация
  private final UUID replyId;
  private final PostId postId;

  private final String message;
  private boolean isActive;

  // Value Objects
  private final AuthorInfo author;

  // Метаданные
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime viewedAt;

  // Приватный конструктор
  private Reply(
      UUID replyId,
      PostId postId,
      AuthorInfo author,
      String message,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    validate(message);
    this.replyId = replyId;
    this.postId = postId;
    this.author = author;
    this.message = message;
    this.isActive = true;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
  }

  public static Reply from(
      UUID replyId,
      PostId postId,
      AuthorInfo author,
      String message,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return new Reply(replyId, postId, author, message, createdAt, updatedAt);
  }

  public static Reply create(
      PostId postId, AuthorInfo author, String message, LocalDateTime createdAt) {
    return new Reply(
        UuidCreator.getTimeOrderedEpoch(), postId, author, message, createdAt, createdAt);
  }

  private void validate(String message) {
    if (message == null || message.trim().isEmpty()) {
      throw new DomainException("Сообщение не может быть пустым");
    }
    if (message.trim().length() < 10) {
      throw new DomainException("Сообщение должно содержать минимум 10 символов");
    }
  }

  @Override
  public int hashCode() {
    return Objects.hash(replyId);
  }
}
