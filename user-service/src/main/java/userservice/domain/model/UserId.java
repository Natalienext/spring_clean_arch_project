package userservice.domain.model;

import com.github.f4b6a3.uuid.UuidCreator;
import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public class UserId {

  private final UUID value;

  public static UserId newId() {
    return new UserId(UuidCreator.getTimeOrderedEpoch());
  }

  public static UserId fromString(String uuid) {
    return new UserId(UUID.fromString(uuid));
  }

  public static UserId fromUuid(UUID uuid) {
    return new UserId(uuid);
  }

  private UserId(UUID value) {
    if (value == null) {
      throw new IllegalArgumentException("UUID не может быть null");
    }
    this.value = value;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    UserId userId = (UserId) o;
    return Objects.equals(value, userId.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }

  @Override
  public String toString() {
    return value.toString();
  }
}
