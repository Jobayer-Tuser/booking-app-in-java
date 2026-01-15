package org.booking.User;

public record UpdateUserRequest(
    String name,
    String displayName,
    String email,
    Long roleId
) {}
