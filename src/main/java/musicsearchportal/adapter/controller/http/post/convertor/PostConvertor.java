package musicsearchportal.adapter.controller.http.post.convertor;

import java.time.Instant;
import musicsearchportal.adapter.controller.http.post.request.CreatePostRequest;
import musicsearchportal.adapter.controller.http.post.response.CreatePostResponse;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;

public final class PostConvertor {

  public static CreatePostParam createRequestToModel(CreatePostRequest request) {

    if (request == null) {
      return CreatePostParam.builder().createdAt(Instant.now()).build();
    }

    return CreatePostParam.builder()
        .title(request.title())
        .description(request.description())
        .authorId(request.authorId())
        .authorName(request.authorName())
        .authorYearsExperience(request.authorYearsExperience())
        .city(request.city())
        .district(request.district())
        .remoteOk(request.remoteOk())
        .genres(request.genres())
        .postType(request.postType())
        .createdAt(Instant.now())
        .build();
  }

  public static CreatePostResponse createResponseToResult(CreatePostResult result) {
    return new CreatePostResponse(result.id());
  }
}
