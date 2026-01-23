package org.booking.users;

import lombok.AllArgsConstructor;
import org.booking.auth.SecuredUser;
import org.booking.exceptions.ResourcesNotFoundException;
import org.booking.roles.RoleInterface;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService
{
    private final UserMapper userMapper;
    private final RoleInterface roleInterface;
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmail(email)
                .map(SecuredUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("users not found!"));
    }

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
    public User findUserById(Long id) {
        return userRepository.findUserById(id)
                .orElseThrow(() -> new ResourcesNotFoundException(String.format("No users found with this %s", id)));
    }

    @Override
    public UserDto getValidatedUser(Long id) {
        var user = findUserById(id);
        return userMapper.toSingleDto(user);
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()-> new ResourcesNotFoundException(String.format("No users found with this %s", email)));
    }

    @Override
    public UserDto updateUser(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id).orElseThrow(
                ()-> new ResourcesNotFoundException(String.format("users with ID %s not found", id)));

        User updateUser = userRepository.save(userMapper.toUpdateEntity(user, request));
        return userMapper.toSingleDto(updateUser);
    }

    @Override
    public Boolean isEmailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
