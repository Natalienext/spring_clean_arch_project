package musicsearchportal.domain.usecase.reply;

import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.boundary.gateway.UserServiceGrpcGateway;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.boundary.usecase.AddReplyUseCase;
import musicsearchportal.domain.exception.AuthorNotFoundException;
import musicsearchportal.domain.exception.PostNotFoundException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class AddReplyUseCaseImpl implements AddReplyUseCase {

  private final PostRepository postRepository;
  private final ReplyRepository replyRepository;
  private final UserServiceGrpcGateway userServiceGrpcGateway;

  @Override
  public AddReplyResult add(AddReplyParam param) {

    PostId postId = PostId.fromUuid(param.getPostId());
    Post post =
        postRepository.findById(postId).orElseThrow(() -> new PostNotFoundException(postId));
    log.info("Найден пост с id: {} для добавления отклика", postId);

    log.info("Проверка автора отклика с id: {} в user-service", param.getAuthorId());
    var userDataOptional = userServiceGrpcGateway.findUserById(param.getAuthorId());

    if (userDataOptional.isEmpty()) {
      log.warn(
          "Автор отклика с id {} не найден в user-service, отклик не будет создан",
          param.getAuthorId());
      throw new AuthorNotFoundException(param.getAuthorId());
    }

    var userData = userDataOptional.get();

    AuthorInfo authorInfo =
        AuthorInfo.from(
            userData.getUserId(), userData.getDisplayName(), userData.getYearsExperience());

    Reply reply = Reply.create(postId, authorInfo, param.getMessage(), LocalDateTime.now());

    post.validateReplyCanBeAdded(reply.getAuthor());
    replyRepository.addReply(reply);

    return new AddReplyResult(reply.getReplyId().toString());
  }
}
