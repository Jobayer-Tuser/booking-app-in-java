package database.factory;

import lombok.RequiredArgsConstructor;
import org.booking.role.Role;
import org.booking.role.RoleRepository;

@RequiredArgsConstructor
public class RoleFactory extends Factory<Role> {

    private final RoleRepository roleRepository;

    @Override
    public Role definition() {
        var role = new Role();

        role.setName("User");
        role.setName("Admin");
        role.setName("Editor");
        role.setName("Operator");

        return roleRepository.save(role);
    }
}
