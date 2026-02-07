package musicsearchportal.domain.model;

import java.util.Objects;
import lombok.Getter;

@Getter
public final class MusicGenre {

  private final String mainGenre;
  private final String subGenre;
  private final String description;

  private MusicGenre(String mainGenre, String subGenre, String description) {
    validate(mainGenre, subGenre);
    this.mainGenre = mainGenre.trim().toLowerCase();
    this.subGenre = subGenre.trim().toLowerCase();
    this.description = (description != null) ? description : "";
  }

  public static MusicGenre create(String mainGenre, String subGenre, String description) {
    return new MusicGenre(mainGenre, subGenre, description);
  }

  private void validate(String mainGenre, String subGenre) {
    if (mainGenre == null || mainGenre.isBlank()) {
      throw new IllegalArgumentException("Основной жанр обязателен");
    }
    if (subGenre == null || subGenre.isBlank()) {
      throw new IllegalArgumentException("Поджанр обязателен");
    }

    String main = mainGenre.toLowerCase();
    String sub = subGenre.toLowerCase();

    if (!sub.contains(main)) {
      throw new IllegalArgumentException("Поджанр должен соответствовать жанру");
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof MusicGenre that)) return false;
    return mainGenre.equals(that.mainGenre) && subGenre.equals(that.subGenre);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mainGenre, subGenre);
  }

  @Override
  public String toString() {
    if (description.isBlank()) {
      return String.format("%s (%s)", mainGenre, subGenre);
    }
    return String.format("%s (%s): %s", mainGenre, subGenre, description);
  }
}
