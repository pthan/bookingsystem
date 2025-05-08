package com.my.bookingsystem.user.repository;

import com.my.bookingsystem.user.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PermissionRepository extends JpaRepository<Permission,Long> {

    List<Permission> findAll();
}
