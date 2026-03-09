package musicsearchportal.adapter.repository.post;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.repository.post.converter.PostConverter;
import musicsearchportal.adapter.repository.post.model.PostDbModel;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
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

  @Override
  public Optional<Post> findById(PostId postId) {

    String postIdStr = postId.getValue().toString();
    PostDbModel postDbModel = mongoTemplate.findById(postIdStr, PostDbModel.class);

    if (postDbModel == null) {
      return Optional.empty();
    }

    Post post = postConverter.toEntity(postDbModel);
    return Optional.of(post);
  }
}
