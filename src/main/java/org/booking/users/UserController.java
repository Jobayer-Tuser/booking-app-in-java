package org.booking.users;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public List<UserDto> index() {
        return userService.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> show(@PathVariable Long id) {
        var user = userService.findUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/register")
    public ResponseEntity<?> create(@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder) {
        if (userService.isEmailExists(request.email())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered!")
            );
        }

        var user = userService.createUser(request);
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(user.id()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UpdateUserRequest request) {
        var user = userService.updateUser(id, request);
        return ResponseEntity.ok(user);
    }
}
