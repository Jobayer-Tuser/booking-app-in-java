package org.booking.user;

import lombok.RequiredArgsConstructor;
import org.booking.role.Role;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserMapper
{
    private final PasswordEncoder passwordEncoder;

    public User toCreateEntity(CreateUserRequest request, Role role)
    {
        return User.builder()
            .name(request.name())
            .displayName(request.displayName())
            .email(request.email())
            .password(passwordEncoder.encode(request.password()))
            .role(role)
            .build();
    }

    public User toUpdateEntity(User user, UpdateUserRequest request)
    {
//        user.setRole(request.roleId());
        user.setName(request.name());
        user.setDisplayName(request.displayName());
        user.setEmail(request.email());

        return user;
    }

    public UserDto toSingleDto(User user)
    {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getDisplayName(),
            user.getEmail(),
            user.getRole().getName()
        );
    }

    public List<UserDto> toMultipleDto(List<User> users)
    {
        return users
            .stream()
            .map(user -> new UserDto(
                user.getId(),
                user.getName(),
                user.getDisplayName(),
                user.getEmail(),
                user.getRole().getName()
            ))
            .toList();
    }
}
