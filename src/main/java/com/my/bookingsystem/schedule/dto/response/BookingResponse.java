package com.my.bookingsystem.schedule.dto.response;

import lombok.Data;
import  java.util.List;
@Data
public class BookingResponse {
    private Long bookingId;
    private String status;
    private Integer totalCreditUsed;
    private List<UsedPackageDetail> usedPackages;

    @Data
    public static class UsedPackageDetail {
        private Long packageId;
        private Integer usedCredit;
        private Long countryId;
    }
}
