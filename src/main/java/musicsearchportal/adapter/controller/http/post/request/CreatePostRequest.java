package musicsearchportal.adapter.controller.http.post.request;

import java.util.List;

public record CreatePostRequest(String author, String description, List<String> hashtags) {}
