package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.schedule.entity.ClassSchedule;
import com.my.bookingsystem.schedule.entity.ClassScheduleDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface ClassScheduleDetailRepository extends JpaRepository<ClassScheduleDetail, Long> {
    List<ClassScheduleDetail> findByClassScheduleId(Long classScheduleId);

    @Query("SELECT cs FROM ClassSchedule cs JOIN FETCH cs.details WHERE cs.startDate >= :startDate")
    List<ClassSchedule> findWithDetails(@Param("startDate") ZonedDateTime startDate);



}
