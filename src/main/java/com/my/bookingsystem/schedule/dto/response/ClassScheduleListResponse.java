package com.my.bookingsystem.schedule.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class ClassScheduleListResponse {
    private List<ClassScheduleItem> items;
    private long totalRecords;
}