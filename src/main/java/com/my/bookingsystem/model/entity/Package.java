package com.my.bookingsystem.model.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.ZonedDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Package {
    public static final String PACKAGE_STATUS_ACTIVE = "Active";
    public static final String PACKAGE_STATUS_INACTIVE = "Inactive";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "package_name",nullable = false)
    private String packageName;

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    private Country country;

    @Column(name = "price",nullable = false)
    private double price;

    @Column(name = "credit",nullable = false)
    private int credit;

    @Column(name = "expire_in",nullable = false)
    private int expireIn;


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
