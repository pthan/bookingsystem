package com.my.bookingsystem.user.controller;

import com.my.bookingsystem.schedule.dto.request.BookingUserSearchRequest;
import com.my.bookingsystem.schedule.service.BookingService;
import com.my.bookingsystem.user.dto.request.UserRequest;
import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.user.entity.CustomUserDetails;
import com.my.bookingsystem.user.entity.Role;
import com.my.bookingsystem.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    BookingService bookingService;
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
    @Operation(summary = "User booking list related with class schedule", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/mybookingclass")
    @PreAuthorize("hasAuthority('USER_READ')")
    public ResponseEntity<ResponseFormat> getUserBookings(BookingUserSearchRequest request, @AuthenticationPrincipal CustomUserDetails user) {
        ResponseFormat response = bookingService.userBookingList(request,user.getId());
        return ResponseEntity.ok(response);
    }
}
