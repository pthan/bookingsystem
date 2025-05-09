package com.my.bookingsystem.domain.shared;

import lombok.Data;

@Data
public class SearchRequest {
    private String keyword;
    private Integer first;
    private Integer max;
    private String orderBy;
    private Boolean asc;
}
