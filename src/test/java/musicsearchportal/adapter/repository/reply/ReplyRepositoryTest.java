package musicsearchportal.adapter.repository.reply;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.UUID;
import musicsearchportal.adapter.repository.post.model.PostDbModel;
import musicsearchportal.adapter.repository.reply.converter.ReplyConverter;
import musicsearchportal.adapter.repository.reply.model.ReplyDbModel;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@ExtendWith(MockitoExtension.class)
public class ReplyRepositoryTest {

  @Mock private MongoTemplate mongoTemplate;

  @Mock private ReplyConverter replyConverter;

  @InjectMocks private ReplyRepositoryImpl replyRepository;

  @Test
  void addReply_Success() {

    Reply reply = mock(Reply.class);
    PostId postId = mock(PostId.class);
    UUID uuid = UUID.randomUUID();

    when(reply.getPostId()).thenReturn(postId);
    when(postId.getValue()).thenReturn(uuid);

    ReplyDbModel replyDbModel = mock(ReplyDbModel.class);
    when(replyConverter.toDbModel(reply)).thenReturn(replyDbModel);

    replyRepository.addReply(reply);

    verify(reply).getPostId();
    verify(postId).getValue();
    verify(replyConverter).toDbModel(reply);
    verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(PostDbModel.class));
  }

  @Test
  void addReply_WhenPostNotFound_StillCallsUpdateFirst() {
    Reply reply = mock(Reply.class);
    PostId postId = mock(PostId.class);
    UUID uuid = UUID.randomUUID();

    when(reply.getPostId()).thenReturn(postId);
    when(postId.getValue()).thenReturn(uuid);

    ReplyDbModel replyDbModel = mock(ReplyDbModel.class);
    when(replyConverter.toDbModel(reply)).thenReturn(replyDbModel);

    replyRepository.addReply(reply);

    verify(reply).getPostId();
    verify(postId).getValue();
    verify(replyConverter).toDbModel(reply);
    verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(PostDbModel.class));
  }

  @Test
  void addReply_WhenPostIdIsNull_ThrowsException() {
    Reply reply = mock(Reply.class);
    when(reply.getPostId()).thenReturn(null);

    assertThrows(NullPointerException.class, () -> replyRepository.addReply(reply));

    verify(replyConverter, never()).toDbModel(any());
    verify(mongoTemplate, never()).updateFirst(any(), any(), (Class<?>) any());
  }
}
