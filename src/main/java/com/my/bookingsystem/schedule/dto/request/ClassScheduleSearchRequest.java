package com.my.bookingsystem.schedule.dto.request;


import com.my.bookingsystem.domain.shared.SearchRequest;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ClassScheduleSearchRequest extends SearchRequest {
    private Integer countryId;
    private LocalDate fromDate;  // e.g., 05/08/2025
    private LocalDate toDate;    // e.g., 05/10/2025
}
