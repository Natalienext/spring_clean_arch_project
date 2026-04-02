package userservice.domain.usecase;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userservice.boundary.repository.UserRepository;
import userservice.boundary.usecase.UserUseCase;
import userservice.domain.exception.UserNotFoundException;
import userservice.domain.model.User;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

  private final UserRepository userRepository;

  @Override
  public User getUser(UUID id) {

    Optional<User> userOptional = userRepository.getUserById(id.toString());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(id);
    }

    return userOptional.get();
  }
}
