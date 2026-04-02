package musicsearchportal.adapter.controller.http.reply.convertor;

import musicsearchportal.adapter.controller.http.reply.request.AddReplyRequest;
import musicsearchportal.adapter.controller.http.reply.response.AddReplyResponse;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public final class ReplyConvertor {

  public static AddReplyParam createRequestToModel(AddReplyRequest request) {

    if (request == null) {
      throw new ResponseStatusException(
          HttpStatus.BAD_REQUEST, "AddReplyRequest не может быть null");
    }

    return AddReplyParam.builder()
        .postId(request.postId())
        .message(request.message())
        .authorId(request.authorId())
        .build();
  }

  public static AddReplyResponse createResponseToResult(AddReplyResult result) {
    return new AddReplyResponse(result.id());
  }
}
