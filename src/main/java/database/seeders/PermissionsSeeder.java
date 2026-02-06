package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.permission.Permission;
import org.booking.permission.PermissionRepository;
import org.booking.roles.Role;
import org.booking.roles.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PermissionsSeeder implements Seeder {

    private final PermissionRepository repository;

    @Override
    public void run(){
        List<String> permissions = List.of("READ_USER", "EDIT_USER", "DELETE_USER");
        List<Permission> roleEntity = permissions.stream()
                .map(Permission::new)
                .toList();

        repository.saveAll(roleEntity);
    }
}
