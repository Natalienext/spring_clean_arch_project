package musicsearchportal.domain.model.post;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public final class PostId {
  private final UUID value;

  public static PostId newId() {
    return new PostId(UUID.randomUUID());
  }

  public static PostId fromString(String uuid) {
    return new PostId(UUID.fromString(uuid));
  }

  public static PostId fromUuid(UUID uuid) {
    return new PostId(uuid);
  }

  private PostId(UUID value) {
    if (value == null) {
      throw new IllegalArgumentException("UUID не может быть null");
    }
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    PostId postId = (PostId) o;
    return Objects.equals(value, postId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
