package musicsearchportal.domain.model.post;

import java.time.LocalDateTime;
import java.util.*;
import lombok.Getter;
import musicsearchportal.domain.exception.PostOperationException;
import musicsearchportal.domain.exception.PostValidationException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.MusicGenre;
import musicsearchportal.domain.model.post.enums.PostStatus;
import musicsearchportal.domain.model.post.enums.PostType;
import musicsearchportal.domain.model.reply.Reply;

@Getter
public class Post {

  // Идентификатор
  private final PostId id;

  // Основные данные
  private String title;
  private String description;

  // Value Objects
  private final AuthorInfo author;
  private Location location;
  private Set<MusicGenre> genres;

  // Sub-entity
  private List<Reply> replies;

  // Состояние
  private PostStatus status;
  private final PostType type;

  // Метаданные
  private final LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime expiresAt;

  private Post(
      PostId id,
      String title,
      String description,
      AuthorInfo author,
      Location location,
      Set<MusicGenre> genres,
      PostType type,
      LocalDateTime createdAt) {
    validate(title, description, genres);
    this.id = id;
    this.title = title;
    this.description = description;
    this.author = author;
    this.location = location;
    this.genres = new HashSet<>(genres);
    this.type = type;
    this.status = PostStatus.DRAFT;
    this.replies = new ArrayList<>();
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
    this.expiresAt = calculateExpirationDate();
  }

  private Post(
      PostId id,
      String title,
      String description,
      AuthorInfo author,
      Location location,
      Set<MusicGenre> genres,
      List<Reply> replies,
      PostType type,
      PostStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime expiresAt) {
    validate(title, description, genres);
    this.id = id;
    this.title = title;
    this.description = description;
    this.author = author;
    this.location = location;
    this.genres = new HashSet<>(genres);
    this.type = type;
    this.status = status;
    this.replies = replies;
    this.createdAt = createdAt;
    this.updatedAt = updatedAt;
    this.expiresAt = expiresAt;
  }

  public static Post from(
      PostId id,
      String title,
      String description,
      AuthorInfo author,
      Location location,
      Set<MusicGenre> genres,
      List<Reply> replies,
      PostType type,
      PostStatus status,
      LocalDateTime createdAt,
      LocalDateTime updatedAt,
      LocalDateTime expiresAt) {
    return new Post(
        id,
        title,
        description,
        author,
        location,
        genres,
        replies,
        type,
        status,
        createdAt,
        updatedAt,
        expiresAt);
  }

  public static Post createFull(
      String title,
      String description,
      AuthorInfo author,
      Location location,
      Set<MusicGenre> genres,
      PostType type,
      LocalDateTime createdAt) {
    return new Post(PostId.newId(), title, description, author, location, genres, type, createdAt);
  }

  public void publish() {
    if (status != PostStatus.DRAFT) {
      throw new PostOperationException("Можно публиковать только черновики");
    }
    if (description == null || description.trim().isEmpty()) {
      throw new PostOperationException("Описание обязательно для публикации");
    }
    if (genres.isEmpty()) {
      throw new PostOperationException("Укажите хотя бы один жанр");
    }

    status = PostStatus.PUBLISHED;
    updatedAt = LocalDateTime.now();
  }

  public void close() {
    if (status != PostStatus.PUBLISHED) {
      throw new PostOperationException("Можно закрыть только опубликованные объявления");
    }
    status = PostStatus.WITHDRAWN;
    updatedAt = LocalDateTime.now();
  }

  public void archive() {
    status = PostStatus.ARCHIVED;
    updatedAt = LocalDateTime.now();
  }

  public void ban() {
    status = PostStatus.BANNED;
    updatedAt = LocalDateTime.now();
  }

  public void validateReplyCanBeAdded(AuthorInfo replier) {
    if (!isActive()) {
      throw new PostOperationException("Нельзя откликаться на неактивные объявления");
    }
    if (replier.getUserId().equals(author.getUserId())) {
      throw new PostOperationException("Нельзя откликаться на своё объявление");
    }
  }

  public void updateContent(
      String title, String description, Location location, Set<MusicGenre> genres) {
    if (!canBeEdited()) {
      throw new PostOperationException("Объявление нельзя редактировать в текущем статусе");
    }

    validate(title, description, genres);
    this.title = title;
    this.description = description;
    this.location = location;
    this.genres = new HashSet<>(genres);
    this.updatedAt = LocalDateTime.now();
  }

  public boolean isActive() {
    return status == PostStatus.PUBLISHED && !isExpired() && !isClosedOrBanned();
  }

  public boolean isExpired() {
    return expiresAt.isBefore(LocalDateTime.now());
  }

  public boolean isClosedOrBanned() {
    return status == PostStatus.WITHDRAWN
        || status == PostStatus.BANNED
        || status == PostStatus.ARCHIVED;
  }

  public boolean canBeEdited() {
    return status == PostStatus.DRAFT || status == PostStatus.PUBLISHED;
  }

  private LocalDateTime calculateExpirationDate() {
    return LocalDateTime.now().plusDays(30);
  }

  private void validate(String title, String description, Set<MusicGenre> genres) {
    if (title == null || title.trim().length() < 5) {
      throw new PostValidationException("Заголовок должен содержать минимум 5 символов");
    }
    if (title.trim().length() > 100) {
      throw new PostValidationException("Заголовок не должен превышать 100 символов");
    }
    if (description != null && description.trim().length() > 2000) {
      throw new PostValidationException("Описание не должно превышать 2000 символов");
    }
    if (genres == null || genres.isEmpty()) {
      throw new PostValidationException("Укажите хотя бы один жанр");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Post post = (Post) o;
    return Objects.equals(id, post.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return String.format(
        "Post{id=%s, title='%s', status=%s, type=%s, replies=%d}",
        id, title, status, type, replies.size());
  }
}
