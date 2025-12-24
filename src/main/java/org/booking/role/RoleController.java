package org.booking.role;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/roles")
public class RoleController {

    private final RoleInterface roleInterface;

    @PostMapping
    public ResponseEntity<RoleDto> store(@RequestBody RoleDto roleDto)
    {
        var role = roleInterface.createRole(roleDto);
        if (role == null){
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(role);
    }
}
