package org.booking.users;

import java.util.List;

/**
 * Core user service interface focusing on user CRUD operations.
 * Verification and authentication concerns are handled by separate services.
 */
public interface UserService {

    /**
     * Creates a new user and triggers verification email process.
     *
     * @param request the user creation request
     * @return the created user DTO
     */
    UserDto createUser(CreateUserRequest request);

    /**
     * Retrieves all users.
     *
     * @return list of all user DTOs
     */
    List<UserDto> getAllUsers();

    /**
     * Retrieves a user by ID.
     *
     * @param id the user ID
     * @return the user DTO
     * @throws org.booking.exceptions.ResourcesNotFoundException if user not found
     */
    User getUserById(Long id);

    /**
     * Retrieves a user by email.
     *
     * @param email the user email
     * @return the user DTO
     * @throws org.booking.exceptions.ResourcesNotFoundException if user not found
     */
    User getUserByEmail(String email);

    /**
     * Updates an existing user.
     *
     * @param id the user ID
     * @param request the update request
     * @return the updated user DTO
     * @throws org.booking.exceptions.ResourcesNotFoundException if user not found
     */
    UserDto updateUser(Long id, UpdateUserRequest request);

    /**
     * Checks if an email already exists in the system.
     *
     * @param email the email to check
     * @return true if email exists, false otherwise
     */
    boolean emailExists(String email);
}