package musicsearchportal.domain.model;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.Getter;

@Getter
public final class MusicGenre {

  private final String mainGenre;
  private final String subGenre;

  private MusicGenre(String mainGenre, String subGenre) {
    validate(mainGenre, subGenre);
    this.mainGenre = mainGenre.trim().toLowerCase();
    this.subGenre = subGenre.trim().toLowerCase();
  }

  public static MusicGenre create(String mainGenre, String subGenre) {
    return new MusicGenre(mainGenre, subGenre);
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

  public static Set<MusicGenre> fromStrings(Set<String> genresSet) {
    if (genresSet == null) {
      return new HashSet<>();
    }
    Set<MusicGenre> result = new HashSet<>();
    Set<String> mainGenres =
        genresSet.stream().filter(g -> !g.contains("_")).collect(Collectors.toSet());

    for (String mainGenre : mainGenres) {
      String prefix = mainGenre + "_";
      List<String> subGenres = genresSet.stream().filter(g -> g.startsWith(prefix)).toList();
      if (subGenres.isEmpty()) {
        result.add(new MusicGenre(mainGenre, null));
      } else {
        for (String subGenre : subGenres) {
          result.add(new MusicGenre(mainGenre, subGenre));
        }
      }
    }

    return result;
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
    return String.format("%s (%s)", mainGenre, subGenre);
  }
}
