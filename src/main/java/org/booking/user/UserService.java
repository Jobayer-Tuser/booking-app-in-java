package org.booking.user;

import lombok.AllArgsConstructor;
import org.booking.exception.ResourcesNotFoundException;
import org.booking.role.RoleInterface;
import org.jspecify.annotations.NullMarked;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class UserService implements UserInterface, UserDetailsService
{
    private final UserMapper userMapper;
    private final RoleInterface roleInterface;
    private final UserRepository userRepository;

    @Override
    public UserDto createUser(CreateUserRequest request) {

        var role = roleInterface.findRoleById(request.roleId());
        var mapUser = userMapper.toCreateEntity(request, role);

        var storedUser = userRepository.save(mapUser);
        return userMapper.toSingleDto(storedUser);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toMultipleDto(userRepository.findAll());
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourcesNotFoundException("User with ID " + id + " not found"));

        User updateUser = userRepository.save(userMapper.toUpdateEntity(user, request));
        return userMapper.toSingleDto(updateUser);
    }

    @Override
    public Boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }


    @Override
    @NullMarked
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var user = userRepository.findByEmail(email).orElseThrow(
                ()-> new UsernameNotFoundException("User did not found"));

        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}
