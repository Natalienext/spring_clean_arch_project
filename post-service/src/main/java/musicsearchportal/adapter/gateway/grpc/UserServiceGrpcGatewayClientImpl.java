package musicsearchportal.adapter.gateway.grpc;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.adapter.gateway.grpc.converter.UserServiceConverter;
import musicsearchportal.adapter.gateway.grpc.exception.UserServiceIntegrationException;
import musicsearchportal.boundary.gateway.UserServiceGrpcGateway;
import musicsearchportal.boundary.model.FindUserResult;
import org.springframework.stereotype.Component;
import userservice.proto.GetUserRequest;
import userservice.proto.GetUserResponse;
import userservice.proto.UserError;
import userservice.proto.UserServiceGrpc;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserServiceGrpcGatewayClientImpl implements UserServiceGrpcGateway {

  private final UserServiceGrpc.UserServiceBlockingStub userServiceStub;
  private final UserServiceConverter userServiceConverter;

  private static final String USER_NOT_FOUND = "USER_NOT_FOUND";

  @Override
  public Optional<FindUserResult> findUserById(UUID userId) {

    try {
      GetUserRequest request = GetUserRequest.newBuilder().setUserId(userId.toString()).build();

      GetUserResponse response = userServiceStub.getUser(request);

      if (response.hasData()) {

        return Optional.of(userServiceConverter.toDomain(response));

      } else if (response.hasError()) {

        UserError error = response.getError();
        String errorCode = error.getErrorCode();

        if (USER_NOT_FOUND.equals(errorCode)) {
          log.warn("Пользователь с id {} не найден: {}", userId, error.getMessage());
          return Optional.empty();
        }

        log.error("Бизнес-ошибка: {} - {}", errorCode, error.getMessage());
        throw new RuntimeException("Бизнес-ошибка: " + error.getMessage());
      }

      return Optional.empty();

    } catch (Exception e) {
      log.error("Неожиданная ошибка при вызове user-service", e);
      throw new UserServiceIntegrationException(e.getMessage());
    }
  }
}
