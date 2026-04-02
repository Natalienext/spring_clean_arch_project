package musicsearchportal.adapter.controller.http.reply.request;

import java.util.UUID;

public record AddReplyRequest(UUID postId, String message, UUID authorId) {}
