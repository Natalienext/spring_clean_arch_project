package musicsearchportal.boundary.usecase;

import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;

public interface CreatePostUseCase {

  CreatePostResult create(CreatePostParam params);
}
