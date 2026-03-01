package musicsearchportal.domain.exception;

import lombok.Getter;
import musicsearchportal.domain.model.post.PostId;

@Getter
public class PostNotFoundException extends RuntimeException {

  private final PostId postId;

  public PostNotFoundException(PostId postId) {
    super("Объявление с id " + postId.getValue() + " не найдено");
    this.postId = postId;
  }
}
