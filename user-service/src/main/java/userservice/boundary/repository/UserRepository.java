package userservice.boundary.repository;

import java.util.Optional;
import userservice.domain.model.User;

public interface UserRepository {

  Optional<User> getUserById(String id);
}
