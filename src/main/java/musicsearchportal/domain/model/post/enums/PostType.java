package musicsearchportal.domain.model.post.enums;

import lombok.Getter;

@Getter
public enum PostType {
  BAND_SEEKING_MUSICIAN("Группа ищет музыканта"),
  MUSICIAN_SEEKING_BAND("Музыкант ищет группу"),
  COMMERCIAL_COLLABORATION("Коммерческое сотрудничество"),
  NON_STANDARD_PROJECT("Нестандартный проект");

  private final String label;

  PostType(String label) {
    this.label = label;
  }

  public static PostType fromLabel(String label) {
    for (PostType type : values()) {
      if (type.label.equals(label)) {
        return type;
      }
    }
    throw new IllegalArgumentException("Неизвестный тип: " + label);
  }
}
