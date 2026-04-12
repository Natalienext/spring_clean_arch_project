package userservice.boundary.usecase;

import java.util.UUID;
import userservice.boundary.model.ChangeUserParam;
import userservice.domain.model.User;

public interface UserUseCase {

  User getUser(UUID id);

  void changeUser(UUID id, ChangeUserParam param);
}
