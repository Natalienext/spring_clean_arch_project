package musicsearchportal.adapter.event;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.boundary.usecase.UpdateUserUseCase;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserEventListener {

  private final UpdateUserUseCase updateUserUseCase;

  @KafkaListener(
      topics = "${spring.kafka.consumer.topic}",
      groupId = "${spring.kafka.consumer.group-id}")
  public void listen(String message) {
    try {
      log.info("Получено сообщение из Kafka: userId = {}", message);

      UUID userId = UUID.fromString(message);
      updateUserUseCase.updateUserInfo(userId);

      log.info("Успешно обработано событие для userId = {}", userId);

    } catch (IllegalArgumentException e) {
      log.error("Неверный формат UUID: {}", message, e);
    } catch (Exception e) {
      log.error("Ошибка при обработке события для userId = {}", message, e);
    }
  }
}
