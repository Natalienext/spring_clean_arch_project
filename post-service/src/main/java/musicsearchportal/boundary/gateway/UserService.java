package musicsearchportal.boundary.gateway;

import java.util.Optional;
import java.util.UUID;
import musicsearchportal.boundary.model.FindUserResult;

public interface UserService {

  Optional<FindUserResult> findUserById(UUID userId);
}
