package org.booking.auth;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.user.UserInterface;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JwtConfig jwtConfig;
    private final UserInterface userInterface;
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response)
    {
        Authentication authRequest = new UsernamePasswordAuthenticationToken(request.email(), request.password());
        authenticationManager.authenticate(authRequest);

        var user = userInterface.findUserByEmail(request.email());
        var accessToken = jwtService.generateAccessToken(user);
        var refreshToken = jwtService.generateRefreshToken(user);

        setCookieWithJakarta(refreshToken.toString(), response);

        return ResponseEntity.ok(
                Map.of("message", "Successfully logged in!", "token", accessToken.toString())
        );
    }

    @PostMapping("/token-refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken)
    {
        var jwt = jwtService.parseToken(refreshToken);

        if (jwt == null || jwt.isExpire()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        var user = userInterface.getUserById(jwt.getUserId());
        var accessToken = jwtService.generateAccessToken(user);

        return ResponseEntity.ok(new JwtResponse(accessToken.toString()));
    }

    private void setCookieWithJakarta(String refreshToken, HttpServletResponse response)
    {
        Cookie cookie = new Cookie("refreshToken", refreshToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/api/auth/login");
        cookie.setMaxAge(jwtConfig.getRefreshTokenExpiration());
        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    @GetMapping("/validated/me")
    public void getAuthenticatedUser()
    {
        authService.getCurentUser();
    }

    private void generateRefreshTokenSpringWay(String refreshToken)
    {
        ResponseCookie cookie2 = ResponseCookie.from("refreshToken", refreshToken)
                .httpOnly(true)
                .path("/api/auth/login")
                .maxAge(604800)
                .secure(false)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<Map<String, String>> handleBadCredentialsException() {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                Map.of("error", "Email or password not matched please try again!")
        );
    }

}
