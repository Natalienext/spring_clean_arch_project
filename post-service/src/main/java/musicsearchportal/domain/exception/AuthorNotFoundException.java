package musicsearchportal.domain.exception;

import java.util.UUID;
import lombok.Getter;

@Getter
public class AuthorNotFoundException extends RuntimeException {

  private final UUID authorId;

  public AuthorNotFoundException(UUID authorId) {
    super("Автор с ID " + authorId + " не найден");
    this.authorId = authorId;
  }
}
