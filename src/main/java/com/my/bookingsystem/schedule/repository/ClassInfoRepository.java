package com.my.bookingsystem.schedule.repository;

import com.my.bookingsystem.schedule.entity.ClassInfo;
import com.my.bookingsystem.schedule.service.ClassInfoService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClassInfoRepository extends JpaRepository<ClassInfo, Long> {
    List<ClassInfo> findByCountryId(Long countryId);

}

