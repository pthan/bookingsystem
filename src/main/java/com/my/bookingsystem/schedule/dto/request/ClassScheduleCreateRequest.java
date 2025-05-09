package com.my.bookingsystem.schedule.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ClassScheduleCreateRequest {
    private Long classInfoId;

    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private Integer availableSlots;
    private List<ScheduleDetailRequest> details;

    @Data
    public static class ScheduleDetailRequest {
        private String sessionDay;
        private String startTime;
        private String endTime;
    }
}
