package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.schedule.entity.Booking;
import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {


    @EntityGraph(attributePaths = { "packageUsages", "packageUsages.creditPackage" })
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findByIdWithPackages(@Param("id") Long id);

}
