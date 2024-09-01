package com.codewithanurag.authentication.service;

import com.codewithanurag.authentication.entity.Role;

import java.util.List;

public interface RoleService {
    void createRole(Role role);

    void updateRole(Long roleId, Role role);

    void deleteRole(Long roleId);

    List<Role> getAllRole();
}
