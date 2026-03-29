package userservice.adapter.controller.grpc;

import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class UserGrpcControllerError {

  public static StatusRuntimeException internalError(Throwable cause) {
    String message = "Пользователь не найден";
    return Status.NOT_FOUND.withDescription(message).withCause(cause).asRuntimeException();
  }
}
