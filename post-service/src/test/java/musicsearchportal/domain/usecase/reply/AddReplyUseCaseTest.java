package musicsearchportal.domain.usecase.reply;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import musicsearchportal.boundary.gateway.UserService;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.model.FindUserResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.domain.exception.AuthorNotFoundException;
import musicsearchportal.domain.exception.DomainException;
import musicsearchportal.domain.exception.PostOperationException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddReplyUseCaseTest {

  @Mock private ReplyRepository replyRepository;
  @Mock private PostRepository postRepository;
  @Mock private UserService userService;

  @InjectMocks private AddReplyUseCaseImpl addReplyUseCase;

  private final UUID postId = UUID.randomUUID();
  private final UUID authorId = UUID.randomUUID();
  private FindUserResult userResponse;

  @BeforeEach
  void setUp() {
    userResponse =
        FindUserResult.builder()
            .userId(authorId.toString())
            .displayName("Иван Петров")
            .yearsExperience(5)
            .build();
  }

  @Test
  void add_Success_WhenAuthorExists() {
    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .build();

    Post post = mock(Post.class);
    when(postRepository.findById(any(PostId.class))).thenReturn(Optional.of(post));
    when(userService.findUserById(authorId)).thenReturn(Optional.of(userResponse));

    AddReplyResult result = addReplyUseCase.add(param);

    assertNotNull(result);
    assertNotNull(result.id());

    verify(postRepository).findById(any(PostId.class));
    verify(userService).findUserById(authorId);
    verify(post).validateReplyCanBeAdded(any(AuthorInfo.class));
    verify(replyRepository).addReply(any(Reply.class));
  }

  @Test
  void add_ThrowsAuthorNotFoundException_WhenAuthorDoesNotExist() {

    PostId validPostId = PostId.fromUuid(postId);
    Post post = mock(Post.class);

    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .build();

    when(postRepository.findById(validPostId)).thenReturn(Optional.of(post));
    when(userService.findUserById(authorId)).thenReturn(Optional.empty());

    AuthorNotFoundException exception =
        assertThrows(AuthorNotFoundException.class, () -> addReplyUseCase.add(param));

    assertEquals(authorId, exception.getAuthorId());
    verify(postRepository).findById(validPostId);
    verify(userService).findUserById(authorId);
    verify(replyRepository, never()).addReply(any(Reply.class));
  }

  @Test
  void add_ThrowsException_WhenValidationFails() {
    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .build();

    Post post = mock(Post.class);
    when(postRepository.findById(any(PostId.class))).thenReturn(Optional.of(post));
    when(userService.findUserById(authorId)).thenReturn(Optional.of(userResponse));
    doThrow(new PostOperationException("Нельзя откликаться на своё объявление"))
        .when(post)
        .validateReplyCanBeAdded(any(AuthorInfo.class));

    PostOperationException exception =
        assertThrows(PostOperationException.class, () -> addReplyUseCase.add(param));

    assertEquals("Нельзя откликаться на своё объявление", exception.getMessage());
    verify(postRepository).findById(any(PostId.class));
    verify(userService).findUserById(authorId);
    verify(post).validateReplyCanBeAdded(any(AuthorInfo.class));
    verify(replyRepository, never()).addReply(any(Reply.class));
  }

  @Test
  void add_ThrowsException_WhenMessageIsTooShort() {
    PostId validPostId = PostId.fromUuid(postId);
    Post post = mock(Post.class);

    AddReplyParam param =
        AddReplyParam.builder().postId(postId).authorId(authorId).message("Коротко").build();

    when(postRepository.findById(validPostId)).thenReturn(Optional.of(post));
    when(userService.findUserById(authorId)).thenReturn(Optional.of(userResponse));

    DomainException exception =
        assertThrows(DomainException.class, () -> addReplyUseCase.add(param));

    assertEquals("Сообщение должно содержать минимум 10 символов", exception.getMessage());
    verify(postRepository).findById(validPostId);
    verify(userService).findUserById(authorId);
    verify(replyRepository, never()).addReply(any(Reply.class));
  }
}
