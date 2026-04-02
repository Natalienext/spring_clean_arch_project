package userservice.adapter.repository.converter;

import lombok.NoArgsConstructor;
import userservice.adapter.repository.model.UserDbModel;
import userservice.domain.model.User;
import userservice.domain.model.UserId;
import userservice.domain.model.UserStatus;

@NoArgsConstructor
public final class UserConverter {

  public static User toEntity(UserDbModel model) {
    return new User(
        UserId.fromString(model.getId()),
        model.getDisplayName(),
        model.getYearsExperience(),
        model.getCreatedAt(),
        UserStatus.valueOf(model.getUserStatus()));
  }

  public static UserDbModel toDbModel(User user) {
    UserDbModel model = new UserDbModel();
    model.setId(user.getUserID().toString());
    model.setDisplayName(user.getDisplayName());
    model.setYearsExperience(user.getYearsExperience());
    model.setCreatedAt(user.getCreatedAt());
    model.setUserStatus(user.getUserStatus().name());

    return model;
  }
}
