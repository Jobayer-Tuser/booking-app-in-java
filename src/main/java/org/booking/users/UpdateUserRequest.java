package org.booking.users;

public record UpdateUserRequest(
    String name,
    String displayName,
    String email,
    Long roleId
) {}
