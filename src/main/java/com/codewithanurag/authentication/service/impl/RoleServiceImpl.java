package com.codewithanurag.authentication.service.impl;

import com.codewithanurag.authentication.entity.Role;
import com.codewithanurag.authentication.repository.RoleRepository;
import com.codewithanurag.authentication.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {
    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public void createRole(Role role) {

    }

    @Override
    public void updateRole(Long roleId, Role role) {

    }

    @Override
    public void deleteRole(Long roleId) {

    }

    @Override
    public List<Role> getAllRole() {
        return roleRepository.findAll();
    }
}
