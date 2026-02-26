package musicsearchportal.domain.usecase.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Set;
import java.util.UUID;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.domain.exception.PostValidationException;
import musicsearchportal.domain.model.post.Post;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CreatePostUseCaseTest {

  @Mock private PostRepository postRepository;

  @InjectMocks private CreatePostUseCaseImpl createPostUseCase;

  @Captor private ArgumentCaptor<Post> postCaptor;

  @Test
  void create_ShouldCreatePostSuccessfully() {

    UUID authorId = UUID.randomUUID();
    Set<String> genres = Set.of("Rock", "Rock_Alternative");

    CreatePostParam params =
        CreatePostParam.builder()
            .title("Ищем гитариста")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .city("Москва")
            .district("ЦАО")
            .remoteOk(true)
            .genres(genres)
            .postType("BAND_SEEKING_MUSICIAN")
            .build();

    CreatePostResult result = createPostUseCase.create(params);

    assertNotNull(result);
    assertNotNull(result.id());

    verify(postRepository, times(1)).save(postCaptor.capture());

    Post capturedPost = postCaptor.getValue();
    assertEquals(params.getTitle(), capturedPost.getTitle());
    assertEquals(params.getDescription(), capturedPost.getDescription());
    assertEquals(authorId, capturedPost.getAuthor().getUserId());
    assertEquals("BAND_SEEKING_MUSICIAN", capturedPost.getType().toString());
  }

  @Test
  void create_ShouldThrowException_WhenAuthorYearsExperienceIsNegative() {

    UUID authorId = UUID.randomUUID();
    Set<String> genres = Set.of("Rock", "Rock_Alternative");

    CreatePostParam params =
        CreatePostParam.builder()
            .title("Ищем гитариста")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(-5)
            .city("Москва")
            .district("ЦАО")
            .remoteOk(true)
            .genres(genres)
            .postType("BAND_SEEKING_MUSICIAN")
            .build();

    IllegalArgumentException exception =
        assertThrows(IllegalArgumentException.class, () -> createPostUseCase.create(params));

    assertEquals("Опыт не может быть отрицательным", exception.getMessage());
    verify(postRepository, never()).save(any(Post.class));
  }

  @Test
  void create_ShouldThrowPostValidationException_WhenTitleIsShorterThan5Characters() {

    UUID authorId = UUID.randomUUID();
    Set<String> genres = Set.of("Rock", "Rock_Alternative");

    CreatePostParam params =
        CreatePostParam.builder()
            .title("рок")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
            .authorName("Иван Петров")
            .authorYearsExperience(5)
            .city("Москва")
            .district("ЦАО")
            .remoteOk(true)
            .genres(genres)
            .postType("BAND_SEEKING_MUSICIAN")
            .build();

    PostValidationException exception =
        assertThrows(PostValidationException.class, () -> createPostUseCase.create(params));

    assertEquals("Заголовок должен содержать минимум 5 символов", exception.getMessage());
    verify(postRepository, never()).save(any(Post.class));
  }
}
