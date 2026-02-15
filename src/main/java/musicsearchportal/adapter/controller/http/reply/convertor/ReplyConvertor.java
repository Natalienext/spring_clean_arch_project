package musicsearchportal.adapter.controller.http.reply.convertor;

import java.time.Instant;
import musicsearchportal.adapter.controller.http.reply.request.AddReplyRequest;
import musicsearchportal.adapter.controller.http.reply.response.AddReplyResponse;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;

public final class ReplyConvertor {

  public static AddReplyParam createRequestToModel(AddReplyRequest request) {

    if (request == null) {
      return AddReplyParam.builder().createdAt(Instant.now()).build();
    }

    return AddReplyParam.builder()
        .postId(request.postId())
        .message(request.message())
        .authorId(request.authorId())
        .authorName(request.authorName())
        .authorYearsExperience(request.authorYearsExperience())
        .createdAt(Instant.now())
        .build();
  }

  public static AddReplyResponse createResponseToResult(AddReplyResult result) {
    return new AddReplyResponse(result.id());
  }
}
