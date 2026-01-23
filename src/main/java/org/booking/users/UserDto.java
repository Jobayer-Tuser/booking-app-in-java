package org.booking.users;

public record UserDto(
    Long id,
    String name,
    String displayName,
    String email,
    String role
) {}
