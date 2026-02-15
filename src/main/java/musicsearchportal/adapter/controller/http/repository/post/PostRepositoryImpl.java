package musicsearchportal.adapter.controller.http.repository.post;

import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.controller.http.repository.post.converter.PostConverter;
import musicsearchportal.adapter.controller.http.repository.post.model.PostDbModel;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.domain.model.post.Post;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostRepositoryImpl implements PostRepository {

  private final MongoTemplate mongoTemplate;
  private final PostConverter postConverter;

  @Override
  public void save(Post post) {

    PostDbModel postDbModel = postConverter.toDbModel(post);
    mongoTemplate.save(postDbModel);
  }
}
