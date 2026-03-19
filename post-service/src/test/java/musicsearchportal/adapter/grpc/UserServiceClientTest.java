package musicsearchportal.adapter.grpc;

import musicsearchportal.adapter.gateway.grpc.UserServiceClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import userservice.proto.GetUserResponse;

import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Import(GripMockTestConfig.class)
@ActiveProfiles("test")
public class UserServiceClientTest {

    @Autowired
    private UserServiceClient userServiceClient;

    @Test
    void findUserById_WhenUserExists_ShouldReturnUser() {

        UUID userId = UUID.fromString("12345678-1234-1234-1234-123456789012");

        Optional<GetUserResponse> result = userServiceClient.findUserById(userId);

        assertThat(result).isPresent();
        assertThat(result.get().getUserId()).isEqualTo(userId.toString());
        assertThat(result.get().getDisplayName()).isEqualTo("Test User");
        assertThat(result.get().getYearsExperience()).isEqualTo(5);
    }

    @Test
    void findUserById_WhenUserNotFound_ShouldReturnEmptyOptional() {

        var userId = UUID.fromString("12345678-5555-1234-1234-123456789012");

        Optional<GetUserResponse> result = userServiceClient.findUserById(userId);
        assertThat(result).isEmpty();
    }

}
