package musicsearchportal.domain.usecase.reply;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.boundary.usecase.AddReplyUseCase;
import musicsearchportal.domain.exception.PostNotFoundException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AddReplyUseCaseImpl implements AddReplyUseCase {

  private final PostRepository postRepository;
  private final ReplyRepository replyRepository;

  @Override
  public AddReplyResult add(AddReplyParam param) {

    Reply reply =
        Reply.create(
            PostId.fromUuid(param.getPostId()),
            AuthorInfo.from(
                param.getAuthorId(), param.getAuthorName(), param.getAuthorYearsExperience()),
            param.getMessage(),
            LocalDateTime.now());

    PostId postId = reply.getPostId();
    Post post =
        postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));

    post.validateReplyCanBeAdded(reply.getAuthor());

    replyRepository.addReply(reply);

    return new AddReplyResult(reply.getReplyId().toString());
  }
}
