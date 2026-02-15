package musicsearchportal.adapter.controller.http.repository.reply;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.controller.http.repository.post.converter.PostConverter;
import musicsearchportal.adapter.controller.http.repository.post.model.PostDbModel;
import musicsearchportal.adapter.controller.http.repository.reply.converter.ReplyConverter;
import musicsearchportal.adapter.controller.http.repository.reply.model.ReplyDbModel;
import musicsearchportal.boundary.repository.ReplyRepository;
import musicsearchportal.domain.model.AuthorInfo;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ReplyRepositoryImpl implements ReplyRepository {

  private final MongoTemplate mongoTemplate;
  private final ReplyConverter replyConverter;
  private final PostConverter postConverter;

  @Override
  public void addReply(Reply reply) {

    PostId postId = PostId.fromUuid(reply.getPostId());
    if (postId.getValue().toString().isEmpty()) {
      throw new IllegalArgumentException("Отклик должен быть связан с объявлением");
    }

    String postIdStr = postId.getValue().toString();

    Query query = new Query(Criteria.where("_id").is(postIdStr));
    PostDbModel postDbModel = mongoTemplate.findOne(query, PostDbModel.class);

    if (postDbModel == null) {
      throw new IllegalStateException("Объявление с id " + postIdStr + " не найдено");
    }

    Post post = postConverter.toEntity(postDbModel);

    AuthorInfo replier = reply.getAuthor();
    post.validateReplyCanBeAdded(replier);

    ReplyDbModel replyDbModel = replyConverter.toDbModel(reply);
    mongoTemplate.save(replyDbModel);

    Query postQuery = new Query(Criteria.where("_id").is(postIdStr));
    Update update = new Update().push("replies", replyDbModel).set("updatedAt", Instant.now());

    mongoTemplate.updateFirst(postQuery, update, PostDbModel.class);
  }
}
