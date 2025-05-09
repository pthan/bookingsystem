package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import com.my.bookingsystem.schedule.entity.ClassSchedule;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ClassScheduleRepository extends JpaRepository<ClassSchedule, Long>, JpaSpecificationExecutor<ClassSchedule> {
    List<ClassSchedule> findByClassInfo_Country_IdAndStartDateAfter(Long countryId, ZonedDateTime now);

    @EntityGraph(value = "ClassSchedule.details", type = EntityGraph.EntityGraphType.LOAD)
    Page<ClassSchedule> findAll(Specification<ClassSchedule> spec, Pageable pageable);

    List<ClassSchedule> findAllByEndDateBeforeAndScheduleClassStatus(ZonedDateTime now, String status);

}
