package userservice.adapter.controller.grpc;

import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import userservice.adapter.controller.grpc.convertor.UserConvertor;
import userservice.boundary.usecase.UserUseCase;
import userservice.domain.exception.UserNotFoundException;
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

    try {

      User user = userUseCase.getUser(UserConvertor.createRequestToModel(request));
      GetUserResponse response = UserConvertor.createResultToResponse(user);
      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (UserNotFoundException e) {
      GetUserResponse response =
          UserConvertor.createErrorResponse(
              "USER_NOT_FOUND", "User with id " + request.getUserId() + " not found");
      responseObserver.onNext(response);
      responseObserver.onCompleted();

    } catch (Exception e) {
      log.error("System error", e);
      responseObserver.onError(UserGrpcControllerError.internalError(e));
    }
  }
}
