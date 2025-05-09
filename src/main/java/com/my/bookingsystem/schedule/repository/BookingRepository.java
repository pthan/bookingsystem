package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.schedule.entity.Booking;
import com.my.bookingsystem.schedule.entity.BookingPackageUsage;
import com.my.bookingsystem.schedule.entity.ClassSchedule;
import com.my.bookingsystem.user.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> , JpaSpecificationExecutor<Booking> {


    @EntityGraph(attributePaths = { "packageUsages", "packageUsages.creditPackage" })
    @Query("SELECT b FROM Booking b WHERE b.id = :id")
    Optional<Booking> findByIdWithPackages(@Param("id") Long id);
    boolean existsByScheduleAndUser(ClassSchedule schedule, User user);

    @EntityGraph(attributePaths = {"schedule", "schedule.classInfo", "schedule.details"})
    @Override
    Page<Booking> findAll(Specification<Booking> spec, Pageable pageable);

    Optional<Booking> findFirstByScheduleAndBookingStatusOrderByCreatedOnAsc(
            ClassSchedule schedule, String bookingStatus
    );
    List<Booking> findAllByScheduleAndBookingStatusAndPaymentStatus(
            ClassSchedule schedule, String bookingStatus, String paymentStatus
    );
}
