package org.booking.User;

import java.util.List;

public interface UserInterface {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);

    User getUserById(Long id);
    List<UserDto> getAllUsers();
    User findUserByEmail(String email);

    Boolean isEmailExists(String email);
}
