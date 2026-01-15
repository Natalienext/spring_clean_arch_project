package music_search_portal.adapter.controller.http.post;

import music_search_portal.adapter.controller.http.post.convertor.PostConvertor;
import music_search_portal.adapter.controller.http.post.request.CreatePostRequest;
import music_search_portal.adapter.controller.http.post.response.CreatePostResponse;
import music_search_portal.boundary.model.CreatePostParam;
import music_search_portal.boundary.model.CreatePostResult;
import music_search_portal.domain.usecase.post.CreatePostUseCaseImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Controller {

  @Autowired CreatePostUseCaseImpl createPostUseCase;

  @PostMapping("/create-post")
  public ResponseEntity<CreatePostResponse> createPost(CreatePostRequest request) {

    CreatePostParam params = PostConvertor.createRequestToModel(request);
    CreatePostResult result = createPostUseCase.create(params);
    return ResponseEntity.ok(PostConvertor.createResponseToResult(result));
  }
}
