package org.booking.roles;

public interface RoleService {
    RoleDto createRole(RoleDto roleDto);
    Role findRoleById(Long id);
}
