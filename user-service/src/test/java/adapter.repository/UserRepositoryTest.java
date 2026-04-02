package adapter.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.mongodb.core.MongoTemplate;
import userservice.adapter.repository.UserRepositoryImpl;
import userservice.adapter.repository.model.UserDbModel;
import userservice.domain.model.User;

@ExtendWith(MockitoExtension.class)
class UserRepositoryTest {

  @Mock private MongoTemplate mongoTemplate;

  @InjectMocks private UserRepositoryImpl userRepository;

  @Test
  void getUserById_WithValidId_ShouldReturnUser() {

    String userId = "123e4567-e89b-12d3-a456-426614174000";
    UserDbModel dbModel = new UserDbModel();
    dbModel.setId(userId);
    dbModel.setDisplayName("Тестовый пользователь");
    dbModel.setUserStatus("ACTIVE");

    when(mongoTemplate.findById(eq(userId), eq(UserDbModel.class))).thenReturn(dbModel);

    Optional<User> result = userRepository.getUserById(userId);

    assertTrue(result.isPresent());
    assertEquals(userId, result.get().getUserID().toString());
  }

  @Test
  void getUserById_WithInvalidId_ShouldReturnEmptyOptional() {

    String userId = "invalid-id";
    when(mongoTemplate.findById(eq(userId), eq(UserDbModel.class))).thenReturn(null);

    Optional<User> result = userRepository.getUserById(userId);

    assertFalse(result.isPresent());
    verify(mongoTemplate, times(1)).findById(eq(userId), eq(UserDbModel.class));
  }
}
