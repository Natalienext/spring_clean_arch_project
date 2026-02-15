package musicsearchportal.adapter.controller.http.repository.post.converter;

import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import musicsearchportal.adapter.controller.http.repository.post.model.LocationDbModel;
import musicsearchportal.adapter.controller.http.repository.post.model.PostDbModel;
import musicsearchportal.adapter.controller.http.repository.reply.converter.ReplyConverter;
import musicsearchportal.adapter.controller.http.repository.reply.model.ReplyDbModel;
import musicsearchportal.adapter.controller.http.repository.shared.converter.AuthorInfoConverter;
import musicsearchportal.domain.model.post.Location;
import musicsearchportal.domain.model.post.Post;
import musicsearchportal.domain.model.post.PostId;
import musicsearchportal.domain.model.post.enums.PostStatus;
import musicsearchportal.domain.model.post.enums.PostType;
import musicsearchportal.domain.model.reply.Reply;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class PostConverter {

  private final AuthorInfoConverter authorConverter;
  private final MusicGenreConverter genreConverter;
  private final ReplyConverter replyConverter;

  public PostDbModel toDbModel(Post entity) {
    if (entity == null) return null;

    PostDbModel dbModel = new PostDbModel();
    dbModel.setId(entity.getId().getValue().toString());
    dbModel.setTitle(entity.getTitle());
    dbModel.setDescription(entity.getDescription());
    dbModel.setAuthor(authorConverter.toDbModel(entity.getAuthor()));
    dbModel.setLocation(toLocationDbModel(entity.getLocation()));
    dbModel.setGenres(genreConverter.toDbModel(entity.getGenres()));
    dbModel.setReplies(toRepliesDbModel(entity.getReplies()));
    dbModel.setStatus(entity.getStatus().name());
    dbModel.setType(entity.getType().name());
    dbModel.setCreatedAt(entity.getCreatedAt().toInstant(ZoneOffset.UTC));
    dbModel.setUpdatedAt(entity.getUpdatedAt().toInstant(ZoneOffset.UTC));
    dbModel.setExpiresAt(entity.getExpiresAt().toInstant(ZoneOffset.UTC));

    return dbModel;
  }

  public Post toEntity(PostDbModel dbModel) {
    if (dbModel == null) return null;

    PostId postId = PostId.fromString(dbModel.getId());

    return Post.from(
        postId,
        dbModel.getTitle(),
        dbModel.getDescription(),
        authorConverter.toEntity(dbModel.getAuthor()),
        toLocationDomain(dbModel.getLocation()),
        genreConverter.toEntity(dbModel.getGenres()),
        toRepliesDomain(dbModel.getReplies(), postId.getValue()),
        PostType.valueOf(dbModel.getType()),
        PostStatus.valueOf(dbModel.getStatus()),
        dbModel.getCreatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        dbModel.getUpdatedAt().atZone(ZoneId.systemDefault()).toLocalDateTime(),
        dbModel.getExpiresAt().atZone(ZoneId.systemDefault()).toLocalDateTime());
  }

  private LocationDbModel toLocationDbModel(Location entity) {
    if (entity == null) return null;

    return new LocationDbModel(entity.getCity(), entity.getDistrict(), entity.getRemoteOk());
  }

  private Location toLocationDomain(LocationDbModel dbModel) {
    if (dbModel == null) return null;

    return Location.create(dbModel.getCity(), dbModel.getDistrict(), dbModel.getRemoteOk());
  }

  private List<ReplyDbModel> toRepliesDbModel(List<Reply> replies) {
    if (replies == null) return List.of();

    return replies.stream().map(replyConverter::toDbModel).collect(Collectors.toList());
  }

  private List<Reply> toRepliesDomain(List<ReplyDbModel> repliesDb, UUID postId) {
    if (repliesDb == null) return List.of();

    return repliesDb.stream()
        .map(reply -> replyConverter.toEntity(reply, postId))
        .collect(Collectors.toList());
  }
}
