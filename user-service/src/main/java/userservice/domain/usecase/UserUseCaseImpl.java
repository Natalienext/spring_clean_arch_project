package userservice.domain.usecase;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import userservice.boundary.event.UserEventPublisher;
import userservice.boundary.model.ChangeUserParam;
import userservice.boundary.repository.UserRepository;
import userservice.boundary.usecase.UserUseCase;
import userservice.domain.exception.UserNotFoundException;
import userservice.domain.model.User;

@Service
@RequiredArgsConstructor
public class UserUseCaseImpl implements UserUseCase {

  private final UserRepository userRepository;
  private final UserEventPublisher userEventPublisher;

  @Override
  public User getUser(UUID id) {

    Optional<User> userOptional = userRepository.getUserById(id.toString());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(id);
    }

    return userOptional.get();
  }

  @Override
  public void changeUser(UUID id, ChangeUserParam param) {

    Optional<User> userOptional = userRepository.getUserById(id.toString());
    if (userOptional.isEmpty()) {
      throw new UserNotFoundException(id);
    }

    User user = userOptional.get();
    boolean changed = user.merge(param);
    userRepository.changeUser(user);

    if (changed) {
      userEventPublisher.publishUserChangeEvent(id);
    }
  }
}
