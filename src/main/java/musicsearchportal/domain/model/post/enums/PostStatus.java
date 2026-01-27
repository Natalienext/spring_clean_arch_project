package musicsearchportal.domain.model.post.enums;

import lombok.Getter;

@Getter
public enum PostStatus {
  DRAFT("Черновик"),
  PUBLISHED("Опубликовано"),
  BANNED("В блоке"),
  WITHDRAWN("Отозвано"),
  ARCHIVED("В архиве");

  private final String label;

  PostStatus(String label) {
    this.label = label;
  }

  public static PostStatus fromLabel(String label) {
    for (PostStatus status : values()) {
      if (status.label.equals(label)) {
        return status;
      }
    }
    throw new IllegalArgumentException("Неизвестный статус: " + label);
  }
}
