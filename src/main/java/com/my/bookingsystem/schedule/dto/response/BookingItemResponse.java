package com.my.bookingsystem.schedule.dto.response;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class BookingItemResponse {
    private Long bookingId;
    private String bookingDate; // formatted as dd-MM-yyyy
    private String className;
    private Long classScheduleId;
    private ZonedDateTime startDateTime;
    private Integer creditAmount;
    private String bookingStatus;
    private List<ClassScheduleDetailItem> scheduleDetail;
}
