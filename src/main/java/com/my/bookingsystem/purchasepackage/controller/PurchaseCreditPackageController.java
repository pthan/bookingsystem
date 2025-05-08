package com.my.bookingsystem.purchasepackage.controller;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.PurchasePackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.UserCreditPackageSearchRequest;
import com.my.bookingsystem.purchasepackage.service.PurchaseCreditPackageService;
import com.my.bookingsystem.user.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("purchase")
public class PurchaseCreditPackageController {
    @Autowired
    PurchaseCreditPackageService purchaseCreditPackageService;

    @Operation(summary = "purchase package", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/creditpackage")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<ResponseFormat> purchasePackage(
            @RequestBody @Valid PurchasePackageRequest req,
            @AuthenticationPrincipal CustomUserDetails user
    ) {
        log.info("purchasepackage() called by userId={} with payload={}",
                user.getId(), req);

        ResponseFormat responseFormat = purchaseCreditPackageService.purchaseCreditPackage(req, user.getId());
        log.info("createPackage() result: {}", responseFormat);
        return new ResponseEntity<>(responseFormat, HttpStatus.CREATED);
    }

    @Operation(summary = "List user packages", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/user/packagelist")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<ResponseFormat> viewPackage(@RequestBody UserCreditPackageSearchRequest req,  @AuthenticationPrincipal CustomUserDetails user) {
        return ResponseEntity.ok(purchaseCreditPackageService.listUserCreditPackages(req, user.getId()));
    }

}
