package com.my.bookingsystem.purchasepackage.entity;


import com.my.bookingsystem.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Table(name = "purchase_credit_package")
@Getter
@Setter
@NoArgsConstructor
public class PurchaseCreditPackage {
    public static final String PACKAGE_STATUS_ACTIVE = "Active";
    public static final String PACKAGE_STATUS_INACTIVE = "Inactive";
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String guid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id", nullable = false)
    private CreditPackage creditPackage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "available_credit",nullable = false)
    private int availableCredit;

    @Column(name="remaining_credit",nullable = false)
    private Integer remainingCredit;

    @Column(nullable = false)
    private Double amount;

    @Column(name = "expire_in", nullable = false)
    private Integer expireIn; // days until expiry

    @Column(name = "expire_date", nullable = false)
    private ZonedDateTime expireDate;

    @Column(name = "payment_status", nullable = false)
    private String paymentStatus;

    @Column(nullable = false)
    private String status;

    @Column(name = "created_on", nullable = false)
    private ZonedDateTime createdOn;

    @Column(name = "created_by", nullable = false)
    private Long createdBy;

    @Column(name = "updated_on")
    private ZonedDateTime updatedOn;

    @Column(name = "updated_by")
    private Long updatedBy;
}
