package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PermissionRepository extends JpaRepository<Permission, Long> {
}
