package com.my.bookingsystem.user.controller;

import com.my.bookingsystem.user.dto.request.UserRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.user.entity.Role;
import com.my.bookingsystem.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    @Operation(
            summary = "Register a new user (no auth needed)",
            security = {}        // ← empty = _no_ security on this operation
    )
    @PostMapping("/register")
    public ResponseEntity<ResponseFormat> createUser(@RequestBody UserRequest userRequest){
        ResponseFormat format = userService.saveUser( userRequest , Role.ROLE_USER );
        return new ResponseEntity<>( format, HttpStatus.OK);
    }


}
