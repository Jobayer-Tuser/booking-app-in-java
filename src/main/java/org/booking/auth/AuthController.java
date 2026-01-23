package org.booking.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.users.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Map<String, String>> login(@Valid @RequestBody LoginRequest request, HttpServletResponse response) {
        var accessToken = authService.authenticateUser(request, response);
        return ResponseEntity.ok(
                Map.of("message", "Successfully logged in!", "token", accessToken)
        );
    }

    @PostMapping("/token-refresh")
    public ResponseEntity<JwtResponse> refresh(@CookieValue(value = "refreshToken") String refreshToken) {
        var accessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(new JwtResponse(accessToken));
    }

    @GetMapping("/validated/me")
    public User getAuthenticatedUser() {
        return authService.getCurrentUser();
    }
}
