package musicsearchportal.adapter.grpc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;
import java.util.UUID;
import musicsearchportal.boundary.gateway.UserServiceGrpcGateway;
import musicsearchportal.boundary.model.FindUserResult;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@Import(GripMockTestConfig.class)
@ActiveProfiles("test")
public class UserServiceGrpcGatewayClientImplTest {

  @Autowired private UserServiceGrpcGateway userServiceGrpcGateway;

  @Test
  void findUserById_WhenUserExists_ShouldReturnUser() {

    UUID userId = UUID.fromString("12345678-1234-1234-1234-123456789012");

    Optional<FindUserResult> result = userServiceGrpcGateway.findUserById(userId);

    assertThat(result).isPresent();
    assertThat(result.get().getUserId()).isEqualTo(userId.toString());
    assertThat(result.get().getDisplayName()).isEqualTo("Test User");
    assertThat(result.get().getYearsExperience()).isEqualTo(5);
  }

  @Test
  void findUserById_WhenUserNotFound_ShouldReturnEmptyOptional() {

    var userId = UUID.fromString("12345678-5555-1234-1234-123456789011");

    Optional<FindUserResult> result = userServiceGrpcGateway.findUserById(userId);
    assertThat(result).isEmpty();
  }
}
