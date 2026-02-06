package org.booking.roles;

import lombok.RequiredArgsConstructor;
import org.booking.exceptions.ResourcesNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService
{
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    @Override
    public RoleDto createRole(RoleDto roleDto)
    {
       var role = roleRepository.save(roleMapper.toEntity(roleDto));
       return roleMapper.toResponse(role);
    }

    @Override
    public Role findRoleById(Long id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new ResourcesNotFoundException("UserRole ID of " + id + " not found"));
    }
}
