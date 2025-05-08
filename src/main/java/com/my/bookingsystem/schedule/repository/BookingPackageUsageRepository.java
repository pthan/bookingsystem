package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingPackageUsageRepository extends JpaRepository<BookingPackageUsage, Long> {
}
