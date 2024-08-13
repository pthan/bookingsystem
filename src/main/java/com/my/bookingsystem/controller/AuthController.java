package com.my.bookingsystem.controller;
import com.my.bookingsystem.model.request.LoginRequest;
import com.my.bookingsystem.model.response.ResponseFormat;
import com.my.bookingsystem.service.AuthService;
import com.my.bookingsystem.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {


    @Autowired
    AuthService authService;

    @PostMapping("/authenticate")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {

        ResponseFormat format = authService.authenticateUser( loginRequest );
        return new ResponseEntity<>( format, HttpStatus.OK);
    }
}
