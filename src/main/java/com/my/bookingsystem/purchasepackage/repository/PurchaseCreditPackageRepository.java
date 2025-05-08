package com.my.bookingsystem.purchasepackage.repository;

import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.ZonedDateTime;
import java.util.List;

@Repository
public interface PurchaseCreditPackageRepository  extends JpaRepository<PurchaseCreditPackage, Long> , JpaSpecificationExecutor<PurchaseCreditPackage> {
    List<PurchaseCreditPackage> findByUserIdOrderByCreatedOnDesc(Long userId);

    @Query("""
    SELECT p FROM PurchaseCreditPackage p
    WHERE p.user.id = :userId
      AND p.creditPackage.country.id = :countryId
      AND p.status = :status
      AND p.expireDate > :now
    ORDER BY p.createdOn ASC
""")
    List<PurchaseCreditPackage> findValidPackagesByUserAndCountry(
            @Param("userId") Long userId,
            @Param("countryId") Long countryId,
            @Param("status") String status,
            @Param("now") ZonedDateTime now
    );

}
