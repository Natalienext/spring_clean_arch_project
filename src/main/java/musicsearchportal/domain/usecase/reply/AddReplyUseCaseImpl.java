package musicsearchportal.domain.usecase.reply;

import java.time.ZoneId;
import lombok.RequiredArgsConstructor;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.boundary.usecase.AddReplyUseCase;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddReplyUseCaseImpl implements AddReplyUseCase {

  private final ReplyRepository replyRepository;

  @Override
  public AddReplyResult add(AddReplyParam param) {

    Reply reply =
        Reply.create(
            param.getPostId(),
            AuthorInfo.from(
                param.getAuthorId(), param.getAuthorName(), param.getAuthorYearsExperience()),
            param.getMessage(),
            param.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime());

    replyRepository.addReply(reply);
    return new AddReplyResult(reply.getReplyId().toString());
  }
}
