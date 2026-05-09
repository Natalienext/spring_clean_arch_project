package userservice.boundary.event;

import java.util.UUID;

public interface UserEventPublisher {

  void publishUserChangeEvent(UUID id);
}
