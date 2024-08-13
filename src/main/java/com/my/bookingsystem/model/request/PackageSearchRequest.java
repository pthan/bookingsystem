package com.my.bookingsystem.model.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PackageSearchRequest {
    private String packageName;
    private String guid;
    private String keyword;
    private Integer first;
    private Integer max;
    private String orderBy;
    private Boolean asc;
}
