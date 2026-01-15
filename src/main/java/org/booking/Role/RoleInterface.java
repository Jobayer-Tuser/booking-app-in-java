package org.booking.Role;

public interface RoleInterface {
    RoleDto createRole(RoleDto roleDto);
    Role findRoleById(Long id);
}
