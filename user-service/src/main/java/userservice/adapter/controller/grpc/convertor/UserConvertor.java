package userservice.adapter.controller.grpc.convertor;

import java.util.UUID;
import userservice.domain.model.User;
import userservice.proto.GetUserRequest;
import userservice.proto.GetUserResponse;
import userservice.proto.UserData;
import userservice.proto.UserError;

public class UserConvertor {

  public static UUID createRequestToModel(GetUserRequest request) {
    return UUID.fromString(request.getUserId());
  }

  public static GetUserResponse createResultToResponse(User result) {
    UserData data =
        UserData.newBuilder()
            .setUserId(result.getUserID().toString())
            .setDisplayName(result.getDisplayName())
            .setYearsExperience(result.getYearsExperience())
            .build();
    return GetUserResponse.newBuilder().setData(data).build();
  }

  public static GetUserResponse createErrorResponse(String errorCode, String message) {
    UserError error = UserError.newBuilder().setErrorCode(errorCode).setMessage(message).build();
    return GetUserResponse.newBuilder().setError(error).build();
  }
}
