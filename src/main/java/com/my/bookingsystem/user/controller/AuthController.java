package com.my.bookingsystem.user.controller;
import com.my.bookingsystem.user.dto.request.LoginRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.user.dto.request.UserRequest;
import com.my.bookingsystem.user.entity.Role;
import com.my.bookingsystem.user.service.AuthService;
import com.my.bookingsystem.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("auth")
public class AuthController {


    @Autowired
    AuthService authService;

    @Autowired
    UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        ResponseFormat format = authService.authenticateUser( loginRequest );
        return new ResponseEntity<>( format, HttpStatus.OK);
    }
    @PostMapping("/register")
    public ResponseEntity<ResponseFormat> createAdmin(@RequestBody UserRequest userRequest){
        System.out.println("testing");
        ResponseFormat format = userService.saveUser( userRequest , Role.ROLE_ADMIN );
        return new ResponseEntity<>( null, HttpStatus.OK);
    }
}
