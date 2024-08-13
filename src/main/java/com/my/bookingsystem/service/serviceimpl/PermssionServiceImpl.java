package com.my.bookingsystem.service.serviceimpl;

import com.my.bookingsystem.model.entity.Permission;
import com.my.bookingsystem.repository.PermissionRepository;
import com.my.bookingsystem.service.PermissionService;
import com.my.bookingsystem.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.UUID;

@Service
public class PermssionServiceImpl implements PermissionService {
    @Autowired
    private PermissionRepository permissionRepository;
    public  void createPermissions(){
        Permission permissionRead = new Permission();
        permissionRead.setPermissionName(Permission.READ_PRIVILEGES);

        Permission permissionWrite = new Permission();
        permissionWrite.setPermissionName(Permission.WRITE_PRIVILEGES);
        permissionRead.setGuid(UUID.randomUUID().toString());
        permissionRead.setCreatedOn(ZonedDateTime.now());
        permissionRead.setStatus(Permission.PERMISSION_STATUS_ACTIVE);

        permissionWrite.setGuid(UUID.randomUUID().toString());
        permissionWrite.setCreatedOn(ZonedDateTime.now());
        permissionWrite.setStatus(Permission.PERMISSION_STATUS_ACTIVE);

        permissionRepository.save(permissionRead);
        permissionRepository.save(permissionWrite);
    }
}
