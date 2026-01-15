package music_search_portal.adapter.controller.http.post.convertor;

import java.time.Instant;
import music_search_portal.adapter.controller.http.post.request.CreatePostRequest;
import music_search_portal.adapter.controller.http.post.response.CreatePostResponse;
import music_search_portal.boundary.model.CreatePostParam;
import music_search_portal.boundary.model.CreatePostResult;

public final class PostConvertor {

  public static CreatePostParam createRequestToModel(CreatePostRequest request) {
    return CreatePostParam.builder()
        .author(request.author())
        .description(request.description())
        .hashtags(request.hashtags())
        .createdAt(Instant.now())
        .build();
  }

  public static CreatePostResponse createResponseToResult(CreatePostResult result) {
    return new CreatePostResponse(result.id());
  }
}
