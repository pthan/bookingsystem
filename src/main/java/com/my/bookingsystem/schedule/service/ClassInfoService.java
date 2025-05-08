package com.my.bookingsystem.schedule.service;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.ClassInfoRequest;

public interface ClassInfoService {
    ResponseFormat createClassInfo(ClassInfoRequest request, Long userId);
    ResponseFormat getById(Long id);
}
