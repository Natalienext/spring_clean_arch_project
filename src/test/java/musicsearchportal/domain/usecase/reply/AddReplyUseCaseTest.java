package musicsearchportal.domain.usecase.reply;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.Instant;
import java.util.UUID;
import musicsearchportal.boundary.model.AddReplyParam;
import musicsearchportal.boundary.model.AddReplyResult;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.domain.model.reply.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AddReplyUseCaseTest {

  @Mock private ReplyRepository replyRepository;

  @InjectMocks private AddReplyUseCaseImpl addReplyUseCase;

  private final UUID postId = UUID.randomUUID();
  private final UUID authorId = UUID.randomUUID();
  private final Instant now = Instant.now();

  @Test
  void add_Success() {

    AddReplyParam param =
        AddReplyParam.builder()
            .postId(postId)
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .message("Заинтересован в сотрудничестве, есть опыт")
            .createdAt(now)
            .build();

    AddReplyResult result = addReplyUseCase.add(param);
    assertNotNull(result);
    assertNotNull(result.id());

    verify(replyRepository, times(1)).addReply(any(Reply.class));
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
            .createdAt(now)
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> addReplyUseCase.add(param));

    assertEquals("Сообщение должно содержать минимум 10 символов", exception.getMessage());

    verify(replyRepository, never()).addReply(any(Reply.class));
  }
}
