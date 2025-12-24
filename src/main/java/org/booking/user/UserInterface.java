package org.booking.user;

import java.util.List;
import java.util.Optional;

public interface UserInterface {
    UserDto createUser(CreateUserRequest request);
    UserDto updateUser(Long id, UpdateUserRequest request);

    User getUserById(Long id);
    List<UserDto> getAllUsers();
    User findUserByEmail(String email);

    Boolean isEmailExists(String email);
}
