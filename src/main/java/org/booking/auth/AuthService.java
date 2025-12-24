package org.booking.auth;

import lombok.RequiredArgsConstructor;
import org.booking.user.User;
import org.booking.user.UserInterface;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserInterface userInterface;
    private final AuthenticationManager authenticationManager;

    public User getCurentUser()
    {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        return userInterface.getUserById(userId);
    }
}