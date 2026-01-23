package org.booking.roles;

public interface RoleInterface {
    RoleDto createRole(RoleDto roleDto);
    Role findRoleById(Long id);
}
