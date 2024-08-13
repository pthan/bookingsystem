package com.my.bookingsystem.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
public class User {
    public static final String USER_STATUS_ACTIVE = "Active";
    public static final String USER_STATUS_INACTIVE = "Inactive";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String email;
    private String password;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
    @Column(name = "guid",nullable = false)
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
