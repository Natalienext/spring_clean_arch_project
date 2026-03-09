package musicsearchportal.adapter.controller.http.post;

import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.controller.http.post.convertor.PostConvertor;
import musicsearchportal.adapter.controller.http.post.request.CreatePostRequest;
import musicsearchportal.adapter.controller.http.post.response.CreatePostResponse;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.usecase.CreatePostUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PostController {

  private final CreatePostUseCase createPostUseCase;

  @PostMapping("/create-post")
  public ResponseEntity<CreatePostResponse> createPost(@RequestBody CreatePostRequest request) {

    CreatePostParam params = PostConvertor.createRequestToModel(request);
    CreatePostResult result = createPostUseCase.create(params);
    return ResponseEntity.ok(PostConvertor.createResponseToResult(result));
  }
}
