package com.my.bookingsystem.schedule.dto.response;

import lombok.Data;

import java.time.ZonedDateTime;
import java.util.List;

@Data
public class ClassScheduleItem {
    private long scheduleId;
    private String country;
    private String className;
    private int credit;
    private String duration;
    private ZonedDateTime startDate;
    private ZonedDateTime endDate;
    private List<ClassScheduleDetailItem> classDetailSchedule;
}

