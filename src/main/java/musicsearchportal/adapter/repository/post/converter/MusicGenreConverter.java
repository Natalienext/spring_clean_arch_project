package musicsearchportal.adapter.repository.post.converter;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import musicsearchportal.domain.model.MusicGenre;
import org.springframework.stereotype.Component;

@Component
public final class MusicGenreConverter {

  public Set<String> toDbModel(Set<MusicGenre> genres) {

    if (genres == null || genres.isEmpty()) {
      return Set.of();
    }
    Set<String> result = new HashSet<>();

    for (MusicGenre genre : genres) {
      result.add(genre.getMainGenre());
      String fullGenre = buildFullGenre(genre);
      result.add(fullGenre);
    }

    return result;
  }

  private String buildFullGenre(MusicGenre genre) {
    if (genre.getSubGenre() == null || genre.getSubGenre().isBlank()) {
      return genre.getMainGenre();
    }
    return genre.getMainGenre() + "_" + genre.getSubGenre();
  }

  public Set<MusicGenre> toEntity(Set<String> dbGenres) {
    if (dbGenres == null || dbGenres.isEmpty()) {
      return Set.of();
    }

    Set<MusicGenre> result = new HashSet<>();
    Set<String> mainGenres =
        dbGenres.stream().filter(genre -> !genre.contains("_")).collect(Collectors.toSet());

    for (String mainGenre : mainGenres) {

      Set<String> subGenresForMain = findSubGenres(dbGenres, mainGenre);
      if (subGenresForMain.isEmpty()) {
        result.add(MusicGenre.create(mainGenre, ""));
      } else {
        for (String subGenre : subGenresForMain) {
          result.add(MusicGenre.create(mainGenre, subGenre));
        }
      }
    }

    return result;
  }

  private Set<String> findSubGenres(Set<String> dbGenres, String mainGenre) {
    return dbGenres.stream()
        .filter(genre -> genre.startsWith(mainGenre + "_"))
        .map(genre -> genre.substring(mainGenre.length() + 1))
        .collect(Collectors.toSet());
  }
}
