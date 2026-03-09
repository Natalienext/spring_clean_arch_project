package userservice.adapter.controller.http.user.convertor;

import lombok.NoArgsConstructor;
import userservice.adapter.controller.http.user.response.GetUserResponse;
import userservice.domain.model.User;

@NoArgsConstructor
public final class ResponseConvertor {

  public static GetUserResponse toGetUserResponse(final User user) {

    return new GetUserResponse(
        user.getUserID().getValue(), user.getDisplayName(), user.getYearsExperience());
  }
}
