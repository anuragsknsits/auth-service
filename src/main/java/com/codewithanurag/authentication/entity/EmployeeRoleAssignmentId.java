package com.codewithanurag.authentication.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
public class EmployeeRoleAssignmentId implements Serializable {

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserDTO userDTO;

    @ManyToOne
    @JoinColumn(name = "employee_role_id")
    private EmployeeRole employeeRole;

    // equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeRoleAssignmentId that = (EmployeeRoleAssignmentId) o;
        return Objects.equals(userDTO, that.userDTO) &&
                Objects.equals(employeeRole, that.employeeRole);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userDTO, employeeRole);
    }
}
