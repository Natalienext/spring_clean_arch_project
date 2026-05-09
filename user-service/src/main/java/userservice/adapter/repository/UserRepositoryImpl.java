package userservice.adapter.repository;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import userservice.adapter.repository.converter.UserConverter;
import userservice.adapter.repository.model.UserDbModel;
import userservice.boundary.repository.UserRepository;
import userservice.domain.model.User;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

  private final MongoTemplate mongoTemplate;

  @Override
  public Optional<User> getUserById(String id) {

    UserDbModel userDbModel = mongoTemplate.findById(id, UserDbModel.class);

    if (userDbModel == null) {
      return Optional.empty();
    }

    User user = UserConverter.toEntity(userDbModel);
    return Optional.of(user);
  }

  @Override
  public void changeUser(User user) {

    UserDbModel userDbModel = UserConverter.toDbModel(user);
    mongoTemplate.save(userDbModel);
  }
}
