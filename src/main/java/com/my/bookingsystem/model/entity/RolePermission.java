package com.my.bookingsystem.model.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;


@Table(name = "rolepermission")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class RolePermission {

    public static final String RolePermission_STATUS_ACTIVE = "Active";
    public static final String RolePermission_STATUS_INACTIVE = "Inactive";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="permission_id")
    private Permission permission;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id" , nullable = false)
    private Role role;

    @Column(name = "guid",nullable = true)
    private String guid;
    @Column(name = "created_on",nullable = true)
    private ZonedDateTime createdOn;

    @Column(name = "updated_on",nullable = true)
    private ZonedDateTime updatedOn;
    @Column(name = "updated_by",nullable = true)
    private Long updatedBy;
    @Column(name = "created_by",nullable = true)
    private  Long createdBy;
    @Column(name = "status", nullable = false)
    private String status;
}
