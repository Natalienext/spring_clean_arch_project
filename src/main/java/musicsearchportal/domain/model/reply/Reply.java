package musicsearchportal.domain.model.reply;

import com.github.f4b6a3.uuid.UuidCreator;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;
import musicsearchportal.domain.model.AuthorInfo;

@Getter
public class Reply {

  // Индентификация
  private final UUID replyId;
  private final UUID postId;

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
      UUID postId,
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
      UUID postId,
      AuthorInfo author,
      String message,
      LocalDateTime createdAt,
      LocalDateTime updatedAt) {
    return new Reply(replyId, postId, author, message, createdAt, updatedAt);
  }

  public static Reply create(
      UUID postId, AuthorInfo author, String message, LocalDateTime createdAt) {
    return new Reply(
        UuidCreator.getTimeOrderedEpoch(), postId, author, message, createdAt, createdAt);
  }

  private void validate(String message) {
    if (message == null || message.trim().isEmpty()) {
      throw new IllegalArgumentException("Сообщение не может быть пустым");
    }
    if (message.trim().length() < 10) {
      throw new IllegalArgumentException("Сообщение должно содержать минимум 10 символов");
    }
  }

  public void markAsViewed() {
    if (viewedAt == null) {
      viewedAt = LocalDateTime.now();
      updatedAt = LocalDateTime.now();
    }
  }

  public void deactivate() {
    if (isActive) {
      isActive = false;
      updatedAt = LocalDateTime.now();
    }
  }

  public void restore() {
    if (!isActive) {
      isActive = true;
      updatedAt = LocalDateTime.now();
    }
  }

  public boolean isViewed() {
    return viewedAt != null;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Reply reply = (Reply) o;
    return Objects.equals(replyId, reply.replyId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(replyId);
  }
}
