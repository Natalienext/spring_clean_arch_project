package musicsearchportal.domain.model.reply;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import lombok.Getter;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.MusicGenre;

@Getter
public class Reply {

  // Индентификация
  private final UUID replyId;
  private final UUID postId;

  private final String message;
  private boolean isActive;

  // Value Objects
  private final Set<MusicGenre> genres;
  private final AuthorInfo author;

  // Метаданные
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime viewedAt;

  // Приватный конструктор
  private Reply(
      UUID replyId, UUID postId, AuthorInfo author, String message, Set<MusicGenre> genres) {
    validate(message, genres);
    this.replyId = replyId;
    this.postId = postId;
    this.author = author;
    this.message = message;
    this.genres = new HashSet<>(genres);
    this.isActive = true;
    this.createdAt = LocalDateTime.now();
    this.updatedAt = LocalDateTime.now();
  }

  public static Reply create(
      UUID postId, AuthorInfo author, String message, Set<MusicGenre> genres) {
    return new Reply(UUID.randomUUID(), postId, author, message, genres);
  }

  private void validate(String message, Set<MusicGenre> genres) {
    if (message == null || message.trim().isEmpty()) {
      throw new IllegalArgumentException("Сообщение не может быть пустым");
    }
    if (message.trim().length() < 10) {
      throw new IllegalArgumentException("Сообщение должно содержать минимум 10 символов");
    }
    if (genres == null || genres.isEmpty()) {
      throw new IllegalArgumentException("Должен быть указан хотя бы один жанр");
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
