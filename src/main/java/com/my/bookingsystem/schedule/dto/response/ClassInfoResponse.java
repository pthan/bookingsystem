package com.my.bookingsystem.schedule.dto.response;

import lombok.Data;

@Data
public class ClassInfoResponse {
    private Long id;
    private String className;
    private Integer requiredCredit;
    private Integer duration;
    private String description;
    private Long countryId;
    private String status;
}

