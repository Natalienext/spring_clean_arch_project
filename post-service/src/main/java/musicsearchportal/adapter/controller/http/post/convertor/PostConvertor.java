package musicsearchportal.adapter.controller.http.post.convertor;

import musicsearchportal.adapter.controller.http.post.request.CreatePostRequest;
import musicsearchportal.adapter.controller.http.post.response.CreatePostResponse;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class PostConvertor {

  public static CreatePostParam createRequestToModel(CreatePostRequest request) {

    if (request == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "CreatePostRequest не может быть null");
    }

    return CreatePostParam.builder()
        .title(request.title())
        .description(request.description())
        .authorId(request.authorId())
        .city(request.city())
        .district(request.district())
        .remoteOk(request.remoteOk())
        .genres(request.genres())
        .postType(request.postType())
        .build();
  }

  public static CreatePostResponse createResponseToResult(CreatePostResult result) {
    return new CreatePostResponse(result.id());
  }
}
