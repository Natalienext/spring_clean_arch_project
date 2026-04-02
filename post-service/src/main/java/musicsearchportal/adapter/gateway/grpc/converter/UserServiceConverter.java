package musicsearchportal.adapter.gateway.grpc.converter;

import musicsearchportal.boundary.model.FindUserResult;
import org.springframework.stereotype.Component;
import userservice.proto.GetUserResponse;
import userservice.proto.UserData;

@Component
public class UserServiceConverter {

  public FindUserResult toDomain(GetUserResponse response) {

    UserData data = response.getData();

    return FindUserResult.builder()
        .userId(data.getUserId())
        .displayName(data.getDisplayName())
        .yearsExperience(data.getYearsExperience())
        .build();
  }
}
