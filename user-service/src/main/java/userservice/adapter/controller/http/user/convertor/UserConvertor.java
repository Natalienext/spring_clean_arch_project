package userservice.adapter.controller.http.user.convertor;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import userservice.adapter.controller.http.user.request.ChangeUserRequest;
import userservice.adapter.controller.http.user.response.ChangeUserResponse;
import userservice.adapter.controller.http.user.response.GetUserResponse;
import userservice.boundary.model.ChangeUserParam;
import userservice.domain.model.User;
import userservice.domain.model.UserStatus;

@NoArgsConstructor
@Slf4j
public final class UserConvertor {

  public static final String CHANGE_USER_SUCCESS_RESPONSE =
      "Данные о пользователе успешно обновлены";

  public static GetUserResponse toGetUserResponse(final User user) {

    return new GetUserResponse(
        user.getUserID().getValue(), user.getDisplayName(), user.getYearsExperience());
  }

  public static ChangeUserResponse toChangeUserResponse() {
    return new ChangeUserResponse(CHANGE_USER_SUCCESS_RESPONSE);
  }

  public static ChangeUserParam requestToParam(ChangeUserRequest request) {

    return new ChangeUserParam(
        request.displayName(),
        request.yearsExperience(),
        request.userStatus() == null ? null : parseUserStatus(request.userStatus()));
  }

  private static UserStatus parseUserStatus(String status) {
    try {
      return UserStatus.valueOf(status);
    } catch (IllegalArgumentException e) {
      log.error("Неизвестный статус: {}", status);
      return null;
    }
  }
}
