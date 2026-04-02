package userservice.adapter.controller.http.user.response;

public record ErrorResponse(String message, String details, int status) {}
