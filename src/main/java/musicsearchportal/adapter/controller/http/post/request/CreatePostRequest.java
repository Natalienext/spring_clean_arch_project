package musicsearchportal.adapter.controller.http.post.request;

import java.util.Set;
import java.util.UUID;

public record CreatePostRequest(
    String title,
    String description,
    UUID authorId,
    String authorName,
    int authorYearsExperience,
    String city,
    String district,
    Boolean remoteOk,
    Set<String> genres,
    String postType) {}
