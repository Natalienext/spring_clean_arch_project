package music_search_portal.domain.usecase.post;

import java.util.UUID;
import music_search_portal.boundary.model.CreatePostParam;
import music_search_portal.boundary.model.CreatePostResult;
import music_search_portal.boundary.usecase.CreatePostUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreatePostUseCaseImpl implements CreatePostUseCase {

  @Override
  public CreatePostResult create(CreatePostParam params) {
    return new CreatePostResult(UUID.randomUUID());
  }
}
