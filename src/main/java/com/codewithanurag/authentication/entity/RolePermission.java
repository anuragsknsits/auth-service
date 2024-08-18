package com.codewithanurag.authentication.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "role_permission")
@Data
public class RolePermission {

    @EmbeddedId
    private RolePermissionId id;

    @ManyToOne
    @MapsId("role") // This is the name of the field in RolePermissionId
    @JoinColumn(name = "role_id")
    private Role role;

    @ManyToOne
    @MapsId("permission") // This is the name of the field in RolePermissionId
    @JoinColumn(name = "permission_id")
    private Permission permission;
}
