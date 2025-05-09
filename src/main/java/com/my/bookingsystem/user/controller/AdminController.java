package com.my.bookingsystem.user.controller;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.user.dto.request.UserRequest;
import com.my.bookingsystem.user.entity.Role;
import com.my.bookingsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("admin")
public class AdminController {
    @Autowired
    UserService userService;

    @PostMapping("/register")
    public ResponseEntity<ResponseFormat> createAdmin(@RequestBody UserRequest userRequest){
        ResponseFormat format = userService.saveUser( userRequest , Role.ROLE_ADMIN );
        return new ResponseEntity<>( null, HttpStatus.OK);
    }
}
