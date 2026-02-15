package musicsearchportal.domain.usecase.post;

import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.usecase.CreatePostUseCase;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.MusicGenre;
import musicsearchportal.domain.model.post.Location;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.enums.PostType;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreatePostUseCaseImpl implements CreatePostUseCase {

  private final PostRepository postRepository;

  @Override
  public CreatePostResult create(CreatePostParam params) {

    Post post =
        Post.createFull(
            params.getTitle(),
            params.getDescription(),
            AuthorInfo.from(
                params.getAuthorId(), params.getAuthorName(), params.getAuthorYearsExperience()),
            Location.create(params.getCity(), params.getDistrict(), params.getRemoteOk()),
            MusicGenre.fromStrings(params.getGenres()),
            PostType.valueOf(params.getPostType()),
            params.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());

    postRepository.save(post);

    return new CreatePostResult(post.getId().toString());
  }
}
