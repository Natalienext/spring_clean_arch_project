package musicsearchportal.adapter.repository.reply;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.UUID;
import musicsearchportal.adapter.controller.http.repository.post.converter.PostConverter;
import musicsearchportal.adapter.controller.http.repository.post.model.PostDbModel;
import musicsearchportal.adapter.controller.http.repository.reply.ReplyRepositoryImpl;
import musicsearchportal.adapter.controller.http.repository.reply.converter.ReplyConverter;
import musicsearchportal.adapter.controller.http.repository.reply.model.ReplyDbModel;
import musicsearchportal.domain.exception.PostOperationException;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
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

  @Mock private PostConverter postConverter;

  @InjectMocks private ReplyRepositoryImpl replyRepository;

  private final UUID postUuid = UUID.randomUUID();

  @Test
  void addReply_Success() {

    Reply reply = mock(Reply.class);

    when(reply.getPostId()).thenReturn(postUuid);
    when(reply.getAuthor()).thenReturn(mock(AuthorInfo.class));

    PostDbModel postDbModel = mock(PostDbModel.class);
    when(mongoTemplate.findOne(any(Query.class), eq(PostDbModel.class))).thenReturn(postDbModel);

    Post post = mock(Post.class);
    when(postConverter.toEntity(postDbModel)).thenReturn(post);

    AuthorInfo replier = mock(AuthorInfo.class);
    when(reply.getAuthor()).thenReturn(replier);
    doNothing().when(post).validateReplyCanBeAdded(replier);

    ReplyDbModel replyDbModel = mock(ReplyDbModel.class);
    when(replyConverter.toDbModel(reply)).thenReturn(replyDbModel);

    when(mongoTemplate.save(replyDbModel)).thenReturn(replyDbModel);

    replyRepository.addReply(reply);

    verify(mongoTemplate).findOne(any(Query.class), eq(PostDbModel.class));
    verify(postConverter).toEntity(postDbModel);
    verify(post).validateReplyCanBeAdded(replier);
    verify(replyConverter).toDbModel(reply);
    verify(mongoTemplate).save(replyDbModel);
    verify(mongoTemplate).updateFirst(any(Query.class), any(Update.class), eq(PostDbModel.class));
  }

  @Test
  void addReply_ThrowsException_WhenPostNotFound() {

    Reply reply = mock(Reply.class);
    when(reply.getPostId()).thenReturn(postUuid);
    when(mongoTemplate.findOne(any(Query.class), eq(PostDbModel.class))).thenReturn(null);

    IllegalStateException exception =
        assertThrows(IllegalStateException.class, () -> replyRepository.addReply(reply));

    assertTrue(exception.getMessage().contains("не найдено"));

    verify(mongoTemplate).findOne(any(Query.class), eq(PostDbModel.class));
    verify(postConverter, never()).toEntity(any());
    verify(replyConverter, never()).toDbModel(any());
    verify(mongoTemplate, never()).save(any());
    verify(mongoTemplate, never())
        .updateFirst(any(Query.class), any(Update.class), eq(PostDbModel.class));
  }

  @Test
  void addReply_ThrowsException_WhenValidationFails() {

    Reply reply = mock(Reply.class);
    when(reply.getPostId()).thenReturn(postUuid);

    PostDbModel postDbModel = mock(PostDbModel.class);
    when(mongoTemplate.findOne(any(Query.class), eq(PostDbModel.class))).thenReturn(postDbModel);

    Post post = mock(Post.class);
    when(postConverter.toEntity(postDbModel)).thenReturn(post);

    AuthorInfo replier = mock(AuthorInfo.class);
    when(reply.getAuthor()).thenReturn(replier);

    doThrow(new PostOperationException("Нельзя откликаться на своё объявление"))
        .when(post)
        .validateReplyCanBeAdded(replier);

    PostOperationException exception =
        assertThrows(PostOperationException.class, () -> replyRepository.addReply(reply));

    assertEquals("Нельзя откликаться на своё объявление", exception.getMessage());

    verify(mongoTemplate).findOne(any(Query.class), eq(PostDbModel.class));
    verify(postConverter).toEntity(postDbModel);
    verify(post).validateReplyCanBeAdded(replier);
    verify(replyConverter, never()).toDbModel(any());
    verify(mongoTemplate, never()).save(any());
    verify(mongoTemplate, never())
        .updateFirst(any(Query.class), any(Update.class), eq(PostDbModel.class));
  }
}
