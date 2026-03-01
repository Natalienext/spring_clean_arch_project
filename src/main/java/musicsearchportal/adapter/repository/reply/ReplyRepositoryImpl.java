package musicsearchportal.adapter.repository.reply;

import java.time.Instant;
import lombok.RequiredArgsConstructor;
import musicsearchportal.adapter.repository.post.model.PostDbModel;
import musicsearchportal.adapter.repository.reply.converter.ReplyConverter;
import musicsearchportal.adapter.repository.reply.model.ReplyDbModel;
import musicsearchportal.boundary.repository.ReplyRepository;
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

  @Override
  public void addReply(Reply reply) {

    String postIdStr = reply.getPostId().getValue().toString();
    ReplyDbModel replyDbModel = replyConverter.toDbModel(reply);

    Query query = new Query(Criteria.where("_id").is(postIdStr));
    Update update = new Update().push("replies", replyDbModel).set("updatedAt", Instant.now());

    mongoTemplate.updateFirst(query, update, PostDbModel.class);
  }
}
