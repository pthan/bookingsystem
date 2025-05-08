package com.my.bookingsystem.schedule.entity;

import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "booking_package_usage")
@Getter
@Setter
@NoArgsConstructor
public class BookingPackageUsage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "booking_id")
    private Booking booking;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "package_id")
    private PurchaseCreditPackage creditPackage;

    private Integer usedCredit;
    private Long countryId; // tracks source country
}

