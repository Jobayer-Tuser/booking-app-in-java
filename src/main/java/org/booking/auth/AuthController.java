package org.booking.auth;

import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.booking.jwt.JwtResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
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

    @GetMapping("/validated/profile")
    public ResponseEntity<?> getAuthenticatedUserProfile(@AuthenticationPrincipal SecuredUser user) {

        return ResponseEntity.ok().body(user.getAuthorities());
    }

    @GetMapping("/validated/me")
    @PreAuthorize("hasRole('Editor')")
    public ResponseEntity<?> testPreAuthorizeRule(@AuthenticationPrincipal SecuredUser user) {

        return ResponseEntity.ok().body(user.getAuthorities());
    }
}
