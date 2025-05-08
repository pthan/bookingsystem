package com.my.bookingsystem.purchasepackage.service;

import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageSearchRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.purchasepackage.dto.request.PurchasePackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.UserCreditPackageSearchRequest;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;

public interface PurchaseCreditPackageService {
    ResponseFormat purchaseCreditPackage(PurchasePackageRequest request, Long userId);
    ResponseFormat listUserCreditPackages(UserCreditPackageSearchRequest request, Long userId);
}