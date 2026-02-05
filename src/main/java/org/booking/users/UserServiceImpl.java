package org.booking.users;

import io.jsonwebtoken.JwtException;
import lombok.AllArgsConstructor;
import org.booking.auth.Jwt;
import org.booking.auth.JwtService;
import org.booking.auth.SecuredUser;
import org.booking.enums.VerificationType;
import org.booking.exceptions.ResourcesNotFoundException;
import org.booking.misc.UserCreatedEvent;
import org.booking.roles.RoleInterface;
import org.booking.verificationtoken.VerificationToken;
import org.booking.verificationtoken.VerificationTokenRepository;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService
{
    private final UserMapper userMapper;
    private final RoleInterface roleInterface;
    private final UserRepository userRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final VerificationTokenRepository verificationTokenRepository;
    private final JwtService jwtService;

    @Override
    public @NullMarked UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findByEmailWithPermissions(email)
                .map(SecuredUser::new)
                .orElseThrow(()-> new UsernameNotFoundException("users not found!"));
    }

    @Override
    @Transactional
    public UserDto createUser(CreateUserRequest request) {

        var role = roleInterface.findRoleById(request.roleId());
        var user = userMapper.toCreateEntity(request, role);
        var storedUser = userRepository.save(user);

        String jwtToken = jwtService.generateAccessToken(user).toString();

        var verificationToken = new VerificationToken();
        verificationToken.setUser(user);
        verificationToken.setType(VerificationType.EMAIL_VERIFICATION.name());
        verificationToken.setToken(jwtToken);
        verificationTokenRepository.save(verificationToken);

        eventPublisher.publishEvent(new UserCreatedEvent(this, storedUser, jwtToken));
        return userMapper.toSingleDto(storedUser);
    }

    @Override
    public void verifyUser(String token) {

        try{
            Jwt parseToken = jwtService.parseToken(token);
           
            VerificationToken confirmationToken = verificationTokenRepository.findByToken(token)
                    .orElseThrow(() -> new ResourcesNotFoundException("Token not found"));

            if (confirmationToken.getVerifiedAt() != null) {
                throw new IllegalArgumentException("Token already verified!");
            }

            if (confirmationToken.getExpiredAt().isBefore(LocalDateTime.now())) {
                throw new IllegalArgumentException("This Token is expired please request for new token!");
            }

            confirmationToken.setVerifiedAt(LocalDateTime.now());
            updateUserEmailVerifiedAt(confirmationToken.getUser().getEmail());

        } catch (Exception e) {
            throw new JwtException(e.getMessage());
        }
    }

    private void updateUserEmailVerifiedAt(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("Email not found in the record!"));

        user.setEmailVerifiedAt(LocalDateTime.now());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getAllUsers() {
        return userMapper.toMultipleDto(userRepository.findAll());
    }

    public Page<User> retrieveUsersWithSorted(String field, int offset, int pageSize) {
        Sort sorted = Sort.by(Sort.Direction.ASC, field);
        PageRequest pageRequest = PageRequest.of(offset, pageSize, sorted);
        return userRepository.findAll(pageRequest);
    }

    public CursorPageResponse<User> cursorPaginationPattern(Long cursor, int pageSize) {
        Pageable pageable = PageRequest.of(0, pageSize);
        List<User> users = userRepository.cursorPaginationPattern(cursor, pageable);
        boolean hasNext = users.size() == pageSize;

        Long nextCursor = hasNext
                ? users.getLast().getId()
                : null;
        return new CursorPageResponse<>(users, pageSize, nextCursor, hasNext);
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
