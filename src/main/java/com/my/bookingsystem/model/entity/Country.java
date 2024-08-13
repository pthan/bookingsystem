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
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "country_name",nullable = true)
    private String countryName;

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
    @Column(name = "status", nullable = true)
    private String status;
}
