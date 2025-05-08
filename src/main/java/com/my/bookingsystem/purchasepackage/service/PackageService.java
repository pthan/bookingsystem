package com.my.bookingsystem.purchasepackage.service;

import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageRequest;
import com.my.bookingsystem.purchasepackage.dto.request.CreditPackageSearchRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;

public interface PackageService {
    public ResponseFormat getPackageList(CreditPackageSearchRequest request);
    public ResponseFormat createPackage(CreditPackageRequest request ,long userId);

}
