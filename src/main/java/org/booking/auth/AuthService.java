package org.booking.auth;

import lombok.RequiredArgsConstructor;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.booking.users.User;
import org.booking.users.UserService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final JwtConfig jwtConfig;
    private final JwtService jwtService;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;

    public User getCurrentUser() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        var userId = (Long) authentication.getPrincipal();

        return userService.findUserById(userId);
    }

    public String authenticateUser(LoginRequest request, HttpServletResponse response) {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authRequest);

        var user = userService.findUserByEmail(request.email());
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        setCookie(refreshToken.toString(), response);

        return accessToken.toString();
    }

    public String refreshToken(String refreshToken) {
        Jwt jwt = jwtService.parseToken(refreshToken);

        if (jwt == null || jwt.isExpire()) {
            throw new BadCredentialsException("Jwt token is expired or not valid please provide valid token");
        }

        var user = userService.findUserById(jwt.getUserId());
        var accessToken = jwtService.generateAccessToken(user);
        return accessToken.toString();
    }

    private void setCookie(String refreshToken, HttpServletResponse response) {
        ResponseCookie resCookie = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/api/auth/login")
                .maxAge(604800)
                .secure(false)
                .build();

        response.addHeader(HttpHeaders.SET_COOKIE, resCookie.toString());
    }

    private void setCookieWithJakarta(String refreshToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/login");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
    }
}