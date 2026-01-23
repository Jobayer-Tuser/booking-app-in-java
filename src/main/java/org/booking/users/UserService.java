package org.booking.users;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);

    User findUserById(Long id);
    UserDto getValidatedUser(Long id);
    User findUserByEmail(String email);
    Boolean isEmailExists(String email);
}
