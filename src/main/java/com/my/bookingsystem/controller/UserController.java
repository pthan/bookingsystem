package com.my.bookingsystem.controller;

import com.my.bookingsystem.model.entity.Country;
import com.my.bookingsystem.model.entity.Package;
import com.my.bookingsystem.model.request.UserRequest;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.repository.CountryRepository;
import com.my.bookingsystem.repository.PackageRepository;
import com.my.bookingsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.ZonedDateTime;
import java.util.UUID;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * To register user information
     * @param userRequest
     * @return
     */
    @PostMapping("/register")
    public ResponseEntity<ResponseFormat> createUser(@RequestBody UserRequest userRequest){
        ResponseFormat format = userService.saveUser( userRequest );
        return new ResponseEntity<>( format, HttpStatus.OK);
    }
}
