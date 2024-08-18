package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.RolePermission;
import com.codewithanurag.authentication.entity.RolePermissionId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission, RolePermissionId> {
}