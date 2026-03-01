package musicsearchportal.domain.usecase.reply;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.UUID;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.domain.exception.DomainException;
import musicsearchportal.domain.exception.PostOperationException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddReplyUseCaseTest {

  @Mock private ReplyRepository replyRepository;

  @Mock private PostRepository postRepository;

  @InjectMocks private AddReplyUseCaseImpl addReplyUseCase;

  private final UUID postId = UUID.randomUUID();
  private final UUID authorId = UUID.randomUUID();

  @Test
  void add_Success() {
    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .build();

    Post post = mock(Post.class);
    when(postRepository.findById(any(PostId.class))).thenReturn(Optional.of(post));

    AddReplyResult result = addReplyUseCase.add(param);

    assertNotNull(result);
    assertNotNull(result.id());

    verify(postRepository).findById(any(PostId.class));
    verify(post).validateReplyCanBeAdded(any(AuthorInfo.class));
    verify(replyRepository).addReply(any(Reply.class));
  }

  @Test
  void add_ThrowsException_WhenValidationFails() {
    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .build();

    Post post = mock(Post.class);
    when(postRepository.findById(any(PostId.class))).thenReturn(Optional.of(post));

    doThrow(new PostOperationException("Нельзя откликаться на своё объявление"))
        .when(post)
        .validateReplyCanBeAdded(any(AuthorInfo.class));

    PostOperationException exception =
        assertThrows(PostOperationException.class, () -> addReplyUseCase.add(param));

    assertEquals("Нельзя откликаться на своё объявление", exception.getMessage());

    verify(postRepository).findById(any(PostId.class));
    verify(post).validateReplyCanBeAdded(any(AuthorInfo.class));
    verify(replyRepository, never()).addReply(any(Reply.class));
  }

  @Test
  void add_ThrowsException_WhenMessageIsTooShort() {
    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .message("Коротко") // Меньше 10 символов
            .build();

    DomainException exception =
        assertThrows(DomainException.class, () -> addReplyUseCase.add(param));

    assertEquals("Сообщение должно содержать минимум 10 символов", exception.getMessage());

    verify(postRepository, never()).findById(any());
    verify(replyRepository, never()).addReply(any(Reply.class));
  }
}
