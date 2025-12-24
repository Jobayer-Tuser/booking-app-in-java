package org.booking.user;

public record UserDto(
    Long id,
    String name,
    String displayName,
    String email,
    String role
) {}
