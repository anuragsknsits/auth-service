package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.EmployeeRoleAssignment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRoleAssignmentRepository extends JpaRepository<EmployeeRoleAssignment, Long> {
    void deleteByUserId(Long userId);
}
