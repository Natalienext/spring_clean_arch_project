package musicsearchportal.domain.usecase.post;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import musicsearchportal.boundary.gateway.UserServiceGrpcGateway;
import musicsearchportal.boundary.model.CreatePostParam;
import musicsearchportal.boundary.model.CreatePostResult;
import musicsearchportal.boundary.model.FindUserResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.domain.exception.PostValidationException;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.enums.PostStatus;
import org.junit.jupiter.api.BeforeEach;
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
  @Mock private UserServiceGrpcGateway userServiceGrpcGateway;

  @InjectMocks private CreatePostUseCaseImpl createPostUseCase;

  @Captor private ArgumentCaptor<Post> postCaptor;

  private final UUID authorId = UUID.randomUUID();
  private final Set<String> genres = Set.of("Rock", "Rock_Alternative");
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
  void create_ShouldCreatePostSuccessfully_WhenAuthorExists() {
    CreatePostParam params =
        CreatePostParam.builder()
            .title("Ищем гитариста")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
            .city("Москва")
            .district("ЦАО")
            .remoteOk(true)
            .genres(genres)
            .postType("BAND_SEEKING_MUSICIAN")
            .build();

    when(userServiceGrpcGateway.findUserById(authorId)).thenReturn(Optional.of(userResponse));

    CreatePostResult result = createPostUseCase.create(params);

    assertNotNull(result);
    assertNotNull(result.id());
    verify(postRepository, times(1)).save(postCaptor.capture());

    Post capturedPost = postCaptor.getValue();
    assertEquals(params.getTitle(), capturedPost.getTitle());
    assertEquals(params.getDescription(), capturedPost.getDescription());
    assertEquals(authorId, capturedPost.getAuthor().getUserId());
    assertEquals("Иван Петров", capturedPost.getAuthor().getDisplayName());
    assertEquals(5, capturedPost.getAuthor().getYearsExperience());
    assertEquals("BAND_SEEKING_MUSICIAN", capturedPost.getType().toString());
    assertEquals(PostStatus.PUBLISHED, capturedPost.getStatus());
  }

  @Test
  void create_ShouldCreatePostWithWithdrawnStatus_WhenAuthorDoesNotExist() {
    CreatePostParam params =
        CreatePostParam.builder()
            .title("Ищем гитариста")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
            .city("Москва")
            .district("ЦАО")
            .remoteOk(true)
            .genres(genres)
            .postType("BAND_SEEKING_MUSICIAN")
            .build();

    when(userServiceGrpcGateway.findUserById(authorId)).thenReturn(Optional.empty());

    CreatePostResult result = createPostUseCase.create(params);

    assertNotNull(result);
    assertNotNull(result.id());
    verify(postRepository, times(1)).save(postCaptor.capture());

    Post capturedPost = postCaptor.getValue();
    assertEquals(params.getTitle(), capturedPost.getTitle());
    assertEquals(params.getDescription(), capturedPost.getDescription());
    assertNull(capturedPost.getAuthor());
    assertEquals(PostStatus.WITHDRAWN, capturedPost.getStatus());
  }

  @Test
  void create_ShouldThrowPostValidationException_WhenTitleIsShorterThan5Characters() {
    CreatePostParam params =
        CreatePostParam.builder()
            .title("рок")
            .description("Требуется гитарист для рок-группы")
            .authorId(authorId)
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
    verify(userServiceGrpcGateway, never()).findUserById(any());
  }
}
