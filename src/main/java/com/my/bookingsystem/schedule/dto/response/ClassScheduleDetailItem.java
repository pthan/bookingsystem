package com.my.bookingsystem.schedule.dto.response;

import lombok.Data;

@Data
public class ClassScheduleDetailItem {
    private String sessionDay;
    private String sessionStartTime;
    private String sessionEndTime;
}
