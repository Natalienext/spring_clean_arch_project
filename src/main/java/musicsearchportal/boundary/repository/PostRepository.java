package musicsearchportal.boundary.repository;

import musicsearchportal.domain.model.post.Post;

public interface PostRepository {

  void save(Post post);
}
