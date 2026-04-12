package musicsearchportal.domain.usecase.post;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.boundary.gateway.UserServiceGrpcGateway;
import musicsearchportal.boundary.model.FindUserResult;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.boundary.usecase.UpdateUserUseCase;
import musicsearchportal.domain.model.AuthorInfo;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class UpdateUserUseCaseImpl implements UpdateUserUseCase {

  private final UserServiceGrpcGateway userServiceGrpcGateway;
  private final PostRepository postRepository;

  @Override
  public void updateUserInfo(UUID userId) {

    var userDataOptional = userServiceGrpcGateway.findUserById(userId);
    if (userDataOptional.isEmpty()) {
      log.warn("Не найдено обновление для пользователя id = {}", userId.toString());
      return;
    }

    FindUserResult userResult = userDataOptional.get();
    AuthorInfo authorInfo =
        AuthorInfo.create(userId, userResult.getDisplayName(), userResult.getYearsExperience());

    postRepository.updateUserInfo(authorInfo);
  }
}
