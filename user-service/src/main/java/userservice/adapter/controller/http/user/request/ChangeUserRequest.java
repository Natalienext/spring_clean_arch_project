package userservice.adapter.controller.http.user.request;

public record ChangeUserRequest(String displayName, Integer yearsExperience, String userStatus) {}
