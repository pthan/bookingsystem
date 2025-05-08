package com.my.bookingsystem.user.repository;

import com.my.bookingsystem.user.entity.RolePermission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RolePermissionRepository extends JpaRepository<RolePermission,Long> {


}
