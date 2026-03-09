package userservice.boundary.usecase;

import java.util.UUID;
import userservice.domain.model.User;

public interface UserUseCase {

  User getUser(UUID id);
}
