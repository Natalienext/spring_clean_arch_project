package music_search_portal.boundary.usecase;

import music_search_portal.boundary.model.CreatePostParam;
import music_search_portal.boundary.model.CreatePostResult;

public interface CreatePostUseCase {

  CreatePostResult create(CreatePostParam params);
}
