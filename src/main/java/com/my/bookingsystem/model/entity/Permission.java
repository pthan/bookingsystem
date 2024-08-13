package com.my.bookingsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Permission {

    public static final String READ_PRIVILEGES = "READ_PRIVILEGES";
    public static final String WRITE_PRIVILEGES = "WRITE_PRIVILEGES";
    public static final String PERMISSION_STATUS_ACTIVE = "Active";
    public static final String PERMISSION_STATUS_INACTIVE = "Inactive";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "permission_name", nullable = true)
    private String permissionName;

    @OneToMany(mappedBy = "permission", fetch = FetchType.EAGER)
    private List<RolePermission> rolePermissions;

    @Column(name = "guid", nullable = true)
    private String guid;

    @Column(name = "created_on", nullable = true)
    private ZonedDateTime createdOn;

    @Column(name = "updated_on", nullable = true)
    private ZonedDateTime updatedOn;

    @Column(name = "updated_by", nullable = true)
    private Long updatedBy;

    @Column(name = "created_by", nullable = true)
    private Long createdBy;

    @Column(name = "status", nullable = false)
    private String status;
}
