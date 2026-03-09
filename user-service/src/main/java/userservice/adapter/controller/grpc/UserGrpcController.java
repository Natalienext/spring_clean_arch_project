package userservice.adapter.controller.grpc;

import io.grpc.stub.StreamObserver;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import userservice.boundary.usecase.UserUseCase;
import userservice.domain.model.User;
import userservice.proto.GetUserRequest;
import userservice.proto.GetUserResponse;
import userservice.proto.UserServiceGrpc;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserGrpcController extends UserServiceGrpc.UserServiceImplBase {

  private final UserUseCase userUseCase;

  @Override
  public void getUser(GetUserRequest request, StreamObserver<GetUserResponse> responseObserver) {

    String userId = request.getUserId();

    try {

      UUID uuid = UUID.fromString(userId);
      User user = userUseCase.getUser(uuid);

      GetUserResponse response =
          GetUserResponse.newBuilder()
              .setUserId(user.getUserID().toString())
              .setDisplayName(user.getDisplayName())
              .setYearsExperience(user.getYearsExperience())
              .build();

      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (Exception e) {

      log.error("Ошибка при обработке gRPC запроса для id: {}", userId, e);
      responseObserver.onError(
          io.grpc.Status.NOT_FOUND
              .withDescription("Пользователь не найден: " + userId)
              .withCause(e)
              .asRuntimeException());
    }
  }
}
