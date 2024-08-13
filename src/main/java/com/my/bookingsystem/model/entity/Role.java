package com.my.bookingsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;
import  java.util.*;
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Role {
    public  static  String ROLE_ADMIN="ROLE_ADMIN";
    public  static  String ROLE_USER="ROLE_USER";
    public static final String ROLE_STATUS_ACTIVE = "Active";
    public static final String ROLE_STATUS_INACTIVE = "Inactive";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name="role_id")
    private List<RolePermission> rolePermissions;
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
