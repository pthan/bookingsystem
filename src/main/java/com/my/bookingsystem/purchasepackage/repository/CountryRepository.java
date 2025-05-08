package com.my.bookingsystem.purchasepackage.repository;

import com.my.bookingsystem.purchasepackage.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryRepository extends JpaRepository<Country,Long> {
}
