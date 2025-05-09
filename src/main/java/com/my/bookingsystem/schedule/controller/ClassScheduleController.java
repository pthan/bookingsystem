package com.my.bookingsystem.schedule.controller;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.ClassInfoRequest;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleCreateRequest;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleSearchRequest;
import com.my.bookingsystem.schedule.service.ClassInfoService;
import com.my.bookingsystem.schedule.service.ClassScheduleService;
import com.my.bookingsystem.user.entity.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("schedule")
public class ClassScheduleController {

    @Autowired
    ClassScheduleService classScheduleService;

    @Operation(summary = "Fetch availble class with detail time schedule", security = {})
    @PostMapping("/availableclasses")
    public ResponseEntity<ResponseFormat> getAvailableScheduleClass(
            @RequestBody @Valid ClassScheduleSearchRequest req) {
        log.info("getAvailableClassInfo with payload={}",  req);
        ResponseFormat responseFormat = classScheduleService.listSchedules(req);
        return new ResponseEntity<>(responseFormat, HttpStatus.CREATED);
    }
    @Operation(summary = "Create a new class schedule", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/class")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<ResponseFormat> createClassSchedule(
            @RequestBody @Valid ClassScheduleCreateRequest request,
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("createClassSchedule called by user={} with payload={}", user.getId(), request);
        ResponseFormat responseFormat = classScheduleService.createClassSchedule(request, user.getId());
        return new ResponseEntity<>(responseFormat, HttpStatus.CREATED);
    }


}
