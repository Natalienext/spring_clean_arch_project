package domain.usecase;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import userservice.boundary.repository.UserRepository;
import userservice.domain.exception.UserNotFoundException;
import userservice.domain.model.User;
import userservice.domain.model.UserId;
import userservice.domain.usecase.UserUseCaseImpl;

@ExtendWith(MockitoExtension.class)
class UseruseCaseTest {

  @Mock private UserRepository userRepository;

  @InjectMocks private UserUseCaseImpl userUseCase;

  @Test
  void getUser_WithExistingId_ShouldReturnUser() {
    UUID id = UUID.randomUUID();
    User user = new User(UserId.fromUuid(id), "Тест", 5, null, null);

    when(userRepository.getUserById(id.toString())).thenReturn(Optional.of(user));

    User result = userUseCase.getUser(id);

    assertEquals(id, result.getUserID().getValue());
  }

  @Test
  void getUser_WithNonExistingId_ShouldThrowException() {
    UUID id = UUID.randomUUID();

    when(userRepository.getUserById(id.toString())).thenReturn(Optional.empty());

    assertThrows(UserNotFoundException.class, () -> userUseCase.getUser(id));
  }
}
