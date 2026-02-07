package musicsearchportal.domain.model;

import java.util.Objects;
import java.util.UUID;
import lombok.Getter;

@Getter
public final class AuthorInfo {

  private final UUID userId;
  private final String displayName;
  private final int yearsExperience;

  private AuthorInfo(UUID userId, String displayName, int yearsExperience) {
    validate(userId, displayName, yearsExperience);
    this.userId = userId;
    this.displayName = displayName;
    this.yearsExperience = yearsExperience;
  }

  public static AuthorInfo from(
      UUID userId, String displayName, String userType, int yearsExperience) {
    return new AuthorInfo(userId, displayName, yearsExperience);
  }

  private void validate(UUID userId, String displayName, int yearsExperience) {
    if (userId == null) {
      throw new IllegalArgumentException("ID пользователя обязательно");
    }
    if (displayName == null || displayName.trim().isEmpty()) {
      throw new IllegalArgumentException("Отображаемое имя обязательно");
    }
    if (yearsExperience < 0) {
      throw new IllegalArgumentException("Опыт не может быть отрицательным");
    }
  }

  public String getShortInfo() {
    return String.format("%s (опыт: %d лет)", displayName, yearsExperience);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    AuthorInfo that = (AuthorInfo) o;
    return Objects.equals(userId, that.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(userId);
  }

  @Override
  public String toString() {
    return String.format(
        "AuthorInfo{userId=%s, name='%s', experience=%d}", userId, displayName, yearsExperience);
  }
}
