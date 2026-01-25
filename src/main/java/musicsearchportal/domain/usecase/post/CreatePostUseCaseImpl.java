package musicsearchportal.domain.usecase.post;

import java.util.UUID;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.usecase.CreatePostUseCase;
import org.springframework.stereotype.Service;

@Service
public class CreatePostUseCaseImpl implements CreatePostUseCase {

  @Override
  public CreatePostResult create(CreatePostParam params) {
    return new CreatePostResult(UUID.randomUUID().toString());
  }
}
