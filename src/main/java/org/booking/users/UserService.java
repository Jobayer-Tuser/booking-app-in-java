package org.booking.users;

import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    List<UserDto> getAllUsers();

    UserDto createUser(CreateUserRequest request);
    void verifyUser(String token);
    UserDto updateUser(Long id, UpdateUserRequest request);

    User findUserById(Long id);
    UserDto getValidatedUser(Long id);
    User findUserByEmail(String email);
    Boolean isEmailExists(String email);

    Page<User>  retrieveUsersWithSorted(String field, int offset, int pageSize);
    CursorPageResponse<User> cursorPaginationPattern(Long cursor, int pageSize);
}
