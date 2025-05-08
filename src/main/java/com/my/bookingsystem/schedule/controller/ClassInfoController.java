package com.my.bookingsystem.schedule.controller;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.ClassInfoRequest;
import com.my.bookingsystem.schedule.service.ClassInfoService;
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
@RequestMapping("classinfo")
public class ClassInfoController {

    @Autowired
    ClassInfoService classInfoService;

    @Operation(summary = "Create a new class info", security = @SecurityRequirement(name = "BearerAuth"))
    @PostMapping("/createclass")
    @PreAuthorize("hasAuthority('ADMIN_WRITE')")
    public ResponseEntity<ResponseFormat> createClassInfo(
            @RequestBody @Valid ClassInfoRequest req,
            @AuthenticationPrincipal CustomUserDetails user) {
        log.info("createClassInfo called by user={} with payload={}", user.getId(), req);
        ResponseFormat responseFormat = classInfoService.createClassInfo(req, user.getId());
        return new ResponseEntity<>(responseFormat, HttpStatus.CREATED);
    }

    @Operation(summary = "Find class by class ID", security = @SecurityRequirement(name = "BearerAuth"))
    @GetMapping("/getclass/{id}")
    @PreAuthorize("hasAnyAuthority('ADMIN_READ', 'USER_READ')")
    public ResponseEntity<ResponseFormat> getClassById(
            @PathVariable("id") Long id,
            @AuthenticationPrincipal CustomUserDetails user) {

        log.info("getClassById called by user={} for classId={}", user.getId(), id);
        ResponseFormat response = classInfoService.getById(id);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

}
