package com.my.bookingsystem.schedule.controller;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.BookingRequest;
import com.my.bookingsystem.schedule.dto.request.CancelRequest;
import com.my.bookingsystem.schedule.service.BookingService;
import com.my.bookingsystem.user.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/booking")
@RequiredArgsConstructor
@Slf4j
public class BookingController {

    private  final  BookingService bookingService;

    @Operation(summary = "Book a class using user's available package", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/class")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<ResponseFormat> bookClass(
            @RequestBody @Valid BookingRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("bookClass called by user={} with payload={}", user.getId(), request);
        ResponseFormat responseFormat = bookingService.makeBooking(request.getScheduleId(), user.getId());
        return new ResponseEntity<>(responseFormat, HttpStatus.CREATED);
    }
    @Operation(summary = "Add in waiting list by using credit", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/addtowaitlist")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<ResponseFormat> joinWaitlist( @RequestBody @Valid BookingRequest request,
                                                       @AuthenticationPrincipal CustomUserDetails user) {
        ResponseFormat response = bookingService.joinWaitlist(request.getScheduleId(), user.getId());
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "Cancel Booking and get Refund", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/cancel")
    @PreAuthorize("hasAuthority('USER_WRITE')")
    public ResponseEntity<ResponseFormat> cancelBooking(@RequestBody @Valid CancelRequest request,
                                                        @AuthenticationPrincipal CustomUserDetails user) {
        ResponseFormat response = bookingService.cancelBooking(request.getBookingId(), user.getId());
        return ResponseEntity.ok(response);
    }

}
