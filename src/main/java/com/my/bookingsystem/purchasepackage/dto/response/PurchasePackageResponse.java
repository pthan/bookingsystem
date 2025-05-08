package com.my.bookingsystem.purchasepackage.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Data
public class PurchasePackageResponse {
    private Long id;
    private String packageName;
    private Integer remainingCredit;
    private Integer availableCredit;
    private ZonedDateTime expireDate;
    private String status;
}
