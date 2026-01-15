package database.seeders;

import lombok.RequiredArgsConstructor;
import org.booking.Role.Role;
import org.booking.Role.RoleRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class RolesSeeder implements Seeder {

    private final RoleRepository roleRepository;

    @Override
    public void run(){
        List<String> roles = List.of("User", "Admin", "Editor", "Operator");
        List<Role> roleEntity = roles.stream()
                .map(Role::new)
                .toList();

        roleRepository.saveAll(roleEntity);
    }
}
