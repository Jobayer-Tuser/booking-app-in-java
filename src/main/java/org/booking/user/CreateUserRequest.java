package org.booking.user;

import jakarta.validation.constraints.*;
import org.booking.role.Role;
import org.booking.validation.LowerCase;

public record CreateUserRequest(

        @NotBlank(message = "Name is required!")
        @Size(max = 255)
        String name,

        @NotBlank(message = "Display name is required!")
        @Size(max = 255, message = "Maximum value can contain to 255!")
        String displayName,

        @NotBlank(message = "Email is required!")
        @Email(message = "Must be correct Email type")
        String email,

        @NotBlank(message = "Password is required!")
        @Size(min = 6, message = "Minimum 6 character required!")
        String password,

        @NotNull(message = "UserRole ID is required")
        @Min(value = 1, message = "Minimum 1 value required")
        @Positive(message = "Positive value required")
        Long roleId
) {}
