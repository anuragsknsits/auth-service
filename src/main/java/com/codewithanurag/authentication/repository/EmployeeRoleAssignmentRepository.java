package com.codewithanurag.authentication.repository;

import com.codewithanurag.authentication.entity.EmployeeRoleAssignment;
import com.codewithanurag.authentication.entity.EmployeeRoleAssignmentId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRoleAssignmentRepository extends JpaRepository<EmployeeRoleAssignment, EmployeeRoleAssignmentId> {
    /*void deleteByUserDTO_Id(Long userId);*/
}
