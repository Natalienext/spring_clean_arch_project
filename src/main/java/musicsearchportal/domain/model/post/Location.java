package musicsearchportal.domain.model.post;

import java.util.Objects;
import lombok.Getter;

@Getter
public final class Location {

  private final String city;
  private final String district;
  private final Boolean remoteOk;

  public static Location create(String city, String district, boolean remoteOk) {
    validate(city);
    return new Location(city, district, remoteOk);
  }

  private Location(String city, String district, boolean remoteOk) {
    this.city = city.trim();
    this.district = (district != null) ? district.trim() : null;
    this.remoteOk = remoteOk;
  }

  private static void validate(String city) {
    if (city == null || city.isBlank()) {
      throw new IllegalArgumentException("Город обязателен");
    }
    if (city.length() < 2) {
      throw new IllegalArgumentException("Название города слишком короткое");
    }
  }

  // Есть ли район (или город без района)
  public boolean hasDistrict() {
    return district != null && !district.isBlank();
  }

  // Подходит ли для удаленной работы
  public boolean supportsRemoteWork() {
    return remoteOk;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Location location = (Location) o;
    return remoteOk == location.remoteOk
        && Objects.equals(city, location.city)
        && Objects.equals(district, location.district);
  }

  @Override
  public int hashCode() {
    return Objects.hash(city, district, remoteOk);
  }

  @Override
  public String toString() {
    String locationString = city;
    if (district != null && !district.isBlank()) {
      locationString += ", " + district;
    }
    if (remoteOk) {
      locationString += " (удаленно возможно)";
    }
    return locationString;
  }
}
