package music_search_portal.adapter.controller.http.post.response;

public record ErrorResponse(String message, String details, int status) {}
