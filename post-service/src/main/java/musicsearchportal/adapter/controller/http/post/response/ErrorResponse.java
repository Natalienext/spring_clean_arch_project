package musicsearchportal.adapter.controller.http.post.response;

public record ErrorResponse(String message, String details, int status) {}
