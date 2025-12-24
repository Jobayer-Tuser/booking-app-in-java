package org.booking.role;

public interface RoleInterface {
    RoleDto createRole(RoleDto roleDto);
    Role findRoleById(Long id);
}
