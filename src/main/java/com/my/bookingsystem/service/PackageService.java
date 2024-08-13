package com.my.bookingsystem.service;

import com.my.bookingsystem.model.request.LoginRequest;
import com.my.bookingsystem.model.request.PackageSearchRequest;
import com.my.bookingsystem.model.response.ResponseFormat;

public interface PackageService {
    public ResponseFormat displayPackageList(PackageSearchRequest request);
}
