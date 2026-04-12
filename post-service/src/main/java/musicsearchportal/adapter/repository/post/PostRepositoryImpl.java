package musicsearchportal.adapter.repository.post;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import musicsearchportal.adapter.repository.post.converter.PostConverter;
import musicsearchportal.adapter.repository.post.model.PostDbModel;
import musicsearchportal.boundary.repository.PostRepository;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class PostRepositoryImpl implements PostRepository {

    private final MongoTemplate mongoTemplate;
    private final PostConverter postConverter;

    @Override
    public void save(Post post) {

        PostDbModel postDbModel = postConverter.toDbModel(post);
        mongoTemplate.save(postDbModel);
    }

    @Override
    public Optional<Post> findById(PostId postId) {

        String postIdStr = postId.getValue().toString();
        PostDbModel postDbModel = mongoTemplate.findById(postIdStr, PostDbModel.class);

        if (postDbModel == null) {
            return Optional.empty();
        }

        Post post = postConverter.toEntity(postDbModel);
        return Optional.of(post);
    }

    @Override
    public void updateUserInfo(AuthorInfo authorInfo) {

        String userIdAsString = authorInfo.getUserId().toString();
        Query query = new Query(Criteria.where("author.userId").is(userIdAsString));

        Update update =
                new Update()
                        .set("author.displayName", authorInfo.getDisplayName())
                        .set("author.yearsExperience", authorInfo.getYearsExperience());

        mongoTemplate.updateMulti(query, update, PostDbModel.class);
    }
}
