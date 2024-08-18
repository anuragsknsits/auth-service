package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.EmployeeRole;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRoleRepository extends JpaRepository<EmployeeRole, Long> {
}
