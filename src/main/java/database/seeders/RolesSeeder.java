package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.roles.Role;
import org.booking.roles.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RolesSeeder implements Seeder {

    private final RoleRepository roleRepository;

    @Override
    public void run(){
        List<String> roles = List.of("users", "Admin", "Editor", "Operator");
        List<Role> roleEntity = roles.stream()
                .map(Role::new)
                .toList();

        roleRepository.saveAll(roleEntity);
    }
}
