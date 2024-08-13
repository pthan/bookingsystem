package com.my.bookingsystem.controller;
import com.my.bookingsystem.model.request.PackageSearchRequest;
import com.my.bookingsystem.service.PackageService;
import org.springframework.security.access.prepost.PreAuthorize;

import com.my.bookingsystem.model.request.UserRequest;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("package")
public class PackageController {

    @Autowired
    PackageService packageService;

    @PostMapping("/list")
    @PreAuthorize("hasAuthority('READ_PRIVILEGES')")
    public ResponseEntity<ResponseFormat> createUser(@RequestBody PackageSearchRequest packageSearchRequest){
        ResponseFormat format = packageService.displayPackageList(packageSearchRequest);
        return new ResponseEntity<>( format, HttpStatus.OK);
    }
}
