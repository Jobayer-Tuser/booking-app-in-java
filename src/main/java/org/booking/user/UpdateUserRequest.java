package org.booking.user;

public record UpdateUserRequest(
    String name,
    String displayName,
    String email,
    Long roleId
) {}
