package org.booking.role;

import org.springframework.stereotype.Component;

@Component
public class RoleMapper
{
    public Role toEntity(RoleDto roleDto)
    {
        return Role.builder()
            .name(roleDto.name())
            .build();
    }

    public RoleDto toResponse(Role role)
    {
        return new RoleDto(role.getId(), role.getName());
    }
}