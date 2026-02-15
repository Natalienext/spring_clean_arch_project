package musicsearchportal.adapter.repository.post;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import musicsearchportal.adapter.controller.http.repository.post.PostRepositoryImpl;
import musicsearchportal.adapter.controller.http.repository.post.converter.PostConverter;
import musicsearchportal.adapter.controller.http.repository.post.model.PostDbModel;
import musicsearchportal.domain.model.post.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;

@ExtendWith(MockitoExtension.class)
public class PostRepositoryTest {

  @Mock private MongoTemplate mongoTemplate;

  @Mock private PostConverter postConverter;

  @InjectMocks private PostRepositoryImpl postRepository;

  @Test
  void save_ShouldSuccessfullySavePost() {

    Post post = mock(Post.class);
    PostDbModel postDbModel = mock(PostDbModel.class);

    when(postConverter.toDbModel(post)).thenReturn(postDbModel);
    when(mongoTemplate.save(postDbModel)).thenReturn(postDbModel);

    postRepository.save(post);

    verify(postConverter, times(1)).toDbModel(post);
    verify(mongoTemplate, times(1)).save(postDbModel);
  }

  @Test
  void save_ShouldThrowException_WhenPostIsNull() {

    Post nullPost = null;
    when(postConverter.toDbModel(nullPost)).thenReturn(null);

    doThrow(new IllegalArgumentException("Object to save must not be null"))
        .when(mongoTemplate)
        .save(null);

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> postRepository.save(nullPost));

    assertEquals("Object to save must not be null", exception.getMessage());

    verify(postConverter, times(1)).toDbModel(nullPost);
    verify(mongoTemplate, times(1)).save(null);
  }
}
