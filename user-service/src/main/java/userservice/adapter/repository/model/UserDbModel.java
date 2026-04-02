package userservice.adapter.repository.model;

import java.time.Instant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "user")
public class UserDbModel {

  @Id private String id;
  private String displayName;
  private Integer yearsExperience;
  private Instant createdAt;
  private String userStatus;
}
