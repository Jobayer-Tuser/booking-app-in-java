package org.booking.user;

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
public class UserController
{

    private final UserInterface userInterface;

    @GetMapping
    public List<UserDto> index()
    {
        return userInterface.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> show(@PathVariable Long id)
    {
        var user = userInterface.getUserById(id);

        if (user == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }

    @PostMapping
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest request, UriComponentsBuilder uriBuilder)
    {
        if (userInterface.isEmailExists(request.email())) {
            return ResponseEntity.badRequest().body(
                    Map.of("email", "Email is already registered!")
            );
        }

        var user = userInterface.createUser(request);
        var uri = uriBuilder.path("/api/users/{id}").buildAndExpand(user.id()).toUri();

        return ResponseEntity.created(uri).body(user);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<UserDto> update(@PathVariable Long id, @RequestBody UpdateUserRequest request)
    {
        var user = userInterface.updateUser(id, request);
        if ( user == null ){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(user);
    }
}
