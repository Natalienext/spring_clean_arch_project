package userservice.boundary.model;

import userservice.domain.model.UserStatus;

public record ChangeUserParam(String displayName, Integer yearsExperience, UserStatus userStatus) {}
