package com.my.bookingsystem.schedule.service;

import com.my.bookingsystem.domain.response.ResponseFormat;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleCreateRequest;
import com.my.bookingsystem.schedule.dto.request.ClassScheduleSearchRequest;

public interface ClassScheduleService {
    ResponseFormat listSchedules(ClassScheduleSearchRequest request);
    ResponseFormat createClassSchedule(ClassScheduleCreateRequest req, Long userId);
}
