package com.my.bookingsystem.purchasepackage.controller;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageSearchRequest;
import com.my.bookingsystem.purchasepackage.service.PackageService;
import com.my.bookingsystem.user.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.security.access.prepost.PreAuthorize;

import com.my.bookingsystem.domain.response.ResponseFormat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import static com.my.bookingsystem.user.enums.RoleAndPermissions.ADMIN_WRITE;

@RestController
@RequestMapping("package")
public class CreditPackageController {

    @Autowired
    PackageService packageService;

    @Operation(
            summary = "List packages (user-read)",
            security = {}
    )
    @PostMapping("/list")
    public ResponseEntity<ResponseFormat> viewPackage(@RequestBody CreditPackageSearchRequest packageSearchRequest){
        ResponseFormat format = packageService.getPackageList(packageSearchRequest);
        return new ResponseEntity<>( format, HttpStatus.OK);
    }
    @Operation(
            summary = "Create a package (admin-write)",
            security = @SecurityRequirement(name = "BearerAuth")
    )
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    @PostMapping("/create")
    public ResponseEntity<ResponseFormat> createPackage(
            @RequestBody @Valid CreditPackageRequest req ,  @AuthenticationPrincipal CustomUserDetails user
            ) {
        ResponseFormat fmt = packageService.createPackage(req,user.getId());
        // return 201 Created with the created package in data
        return new ResponseEntity<>(fmt, HttpStatus.CREATED);
    }

}
