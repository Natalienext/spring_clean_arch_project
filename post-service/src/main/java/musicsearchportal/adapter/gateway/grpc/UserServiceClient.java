package musicsearchportal.adapter.gateway.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import userservice.proto.GetUserRequest;
import userservice.proto.GetUserResponse;
import userservice.proto.UserServiceGrpc;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceClient {

  private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;

  public Optional<GetUserResponse> findUserById(UUID userId) {
    try {
      GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId.toString()).build();

      GetUserResponse response = userServiceStub.getUser(request);
      return Optional.ofNullable(response);

    } catch (StatusRuntimeException e) {
      if (e.getStatus().getCode() == Status.Code.NOT_FOUND) {
        log.warn("Пользователь с id {} не найден", userId);
        return Optional.empty();
      }
      log.error("Ошибка gRPC при вызове user-service: {}", e.getStatus(), e);
      throw new RuntimeException("Ошибка при обращении к user-service", e);
    } catch (Exception e) {
      log.error("Неожиданная ошибка при вызове user-service", e);
      throw new RuntimeException("Ошибка при обращении к user-service", e);
    }
  }
}
