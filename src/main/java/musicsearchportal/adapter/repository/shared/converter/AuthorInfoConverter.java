package musicsearchportal.adapter.repository.shared.converter;

import java.util.UUID;
import musicsearchportal.adapter.repository.shared.model.AuthorInfoDbModel;
import musicsearchportal.domain.model.AuthorInfo;
import org.springframework.stereotype.Component;

@Component
public final class AuthorInfoConverter {

  public AuthorInfoDbModel toDbModel(AuthorInfo entity) {

    if (entity == null) return null;
    return new AuthorInfoDbModel(
        entity.getUserId().toString(), entity.getDisplayName(), entity.getYearsExperience());
  }

  public AuthorInfo toEntity(AuthorInfoDbModel dbModel) {

    if (dbModel == null) return null;
    return AuthorInfo.from(
        UUID.fromString(dbModel.getUserId()),
        dbModel.getDisplayName(),
        dbModel.getYearsExperience());
  }
}
