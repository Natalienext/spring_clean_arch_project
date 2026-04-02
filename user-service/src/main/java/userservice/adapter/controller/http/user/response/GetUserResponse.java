package userservice.adapter.controller.http.user.response;

import java.util.UUID;

public record GetUserResponse(UUID userId, String displayName, Integer yearsExperience) {}
