package musicsearchportal.adapter.controller.http.reply;

import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.controller.http.reply.convertor.ReplyConvertor;
import musicsearchportal.adapter.controller.http.reply.request.AddReplyRequest;
import musicsearchportal.adapter.controller.http.reply.response.AddReplyResponse;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.usecase.AddReplyUseCase;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReplyController {

  private final AddReplyUseCase addReplyUseCase;

  @PostMapping("/add-reply")
  public ResponseEntity<AddReplyResponse> addReply(@RequestBody AddReplyRequest request) {

    AddReplyParam param = ReplyConvertor.createRequestToModel(request);
    AddReplyResult result = addReplyUseCase.add(param);
    return ResponseEntity.ok(ReplyConvertor.createResponseToResult(result));
  }
}
