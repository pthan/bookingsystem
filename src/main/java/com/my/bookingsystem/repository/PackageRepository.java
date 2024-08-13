package com.my.bookingsystem.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<com.my.bookingsystem.model.entity.Package,Long> {

    @Query(
            value = "SELECT p.id,p.credit, p.expire_in as expireIn, p.guid, p.package_name as packageName, p.price,c.country_name as countryName," +
                    " p.status,c.id  FROM package p INNER JOIN country c on c.id=p.country_id" +
                    "    WHERE c.status='Active' and p.status='Active' ",nativeQuery = true
    )
    List<IPackage> getAllPackageList( Pageable pageable);

    @Query(
            value = " SELECT COUNT(*) " +
                    " FROM package p INNER JOIN country c on c.id=p.country_id" +
                    "    WHERE c.status='Active' and p.status='Active' " ,nativeQuery = true
    )
    Long countPackage();
    @Query(
            value = "SELECT p.id,p.credit, p.expire_in as expireIn, p.guid, p.package_name as packageName, p.price,c.country_name as countryName," +
                    " p.status,c.id  FROM package p INNER JOIN country c on c.id=p.country_id" +
                    "    WHERE c.status='Active' and p.status='Active' AND" +
                    " (c.country_name=:keyword or p.package_name LIKE '%:keyword%')",nativeQuery = true
    )
    List<IPackage> getAllPackageByKeywords(@Param("keyword") String keyword, Pageable pageable);

    @Query(
            value = " SELECT COUNT(*) " +
                    " FROM package p INNER JOIN country c on c.id=p.country_id" +
                    "    WHERE c.status='Active' and p.status='Active' AND" +
                    " (c.country_name=:keyword or p.package_name LIKE '%:keyword%')",nativeQuery = true
    )
   Long countPackageByKeywords(@Param("keyword") String keyword);


    interface IPackage
    {
        long getId();
        String getPackageName();
        int getCredit();
        int getExpireIn();
        String getGuid();
        int getPrice();
        String getStatus();

        String getCountryName();


    }
}
