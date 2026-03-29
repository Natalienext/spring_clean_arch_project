package musicsearchportal.domain.usecase.post;

import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.boundary.gateway.UserService;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.model.FindUserResult;
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
@Slf4j
public class CreatePostUseCaseImpl implements CreatePostUseCase {

  private final PostRepository postRepository;
  private final UserService userService;

  @Override
  public CreatePostResult create(CreatePostParam params) {

    Post post =
        Post.createFull(
            params.getTitle(),
            params.getDescription(),
            Location.create(params.getCity(), params.getDistrict(), params.getRemoteOk()),
            MusicGenre.fromStrings(params.getGenres()),
            PostType.valueOf(params.getPostType()),
            LocalDateTime.now());

    log.info("Создан пост с id: {} в статусе DRAFT", post.getId());

    Optional<FindUserResult> userDataOptional = userService.findUserById(params.getAuthorId());

    if (userDataOptional.isPresent()) {

      var userData = userDataOptional.get();
      AuthorInfo authorInfo =
          AuthorInfo.from(
              userData.getUserId(), userData.getDisplayName(), userData.getYearsExperience());

      post.assignAuthor(authorInfo);

    } else {
      log.warn("Автор с id {} не найден, пост будет отозван", params.getAuthorId());
      post.withdraw();
    }

    postRepository.save(post);
    return new CreatePostResult(post.getId().toString());
  }
}
