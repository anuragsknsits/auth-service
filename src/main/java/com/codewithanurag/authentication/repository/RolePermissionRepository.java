package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RolePermissionRepository extends JpaRepository<RolePermission, Long> {
}