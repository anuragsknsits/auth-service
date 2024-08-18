package com.codewithanurag.authentication.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "employee_role_assignment")
@Data
public class EmployeeRoleAssignment {

    @EmbeddedId
    private EmployeeRoleAssignmentId id;
}
