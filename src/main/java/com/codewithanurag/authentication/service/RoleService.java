package com.codewithanurag.authentication.service;

import com.codewithanurag.authentication.entity.Role;

public interface RoleService {
    void createRole(Role role);

    void updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);
}
