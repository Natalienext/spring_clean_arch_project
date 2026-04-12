package userservice.adapter.event;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import userservice.boundary.event.UserEventPublisher;

@Slf4j
@Component
public class UserEventPublisherImpl implements UserEventPublisher {

  @Autowired private KafkaTemplate<String, String> kafkaTemplate;

  @Value("${spring.kafka.topic}")
  private String topic;

  @Override
  public void publishUserChangeEvent(UUID id) {
    try {
      log.info("Отправка события в Kafka: userId = {}", id);
      kafkaTemplate.send(topic, id.toString()).get(5, TimeUnit.SECONDS);

    } catch (Exception e) {
      log.error(
          "Ошибка при отправке события в Kafka: userId = {}, ошибка: {}", id, e.getMessage(), e);
      throw new RuntimeException("Не удалось отправить событие в Kafka для userId: " + id, e);
    }
  }
}
