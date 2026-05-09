package musicsearchportal.boundary.repository;

import java.util.Optional;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;

public interface PostRepository {

  void save(Post post);

  Optional<Post> findById(PostId postId);

  void updateUserInfo(AuthorInfo authorInfo);
}
