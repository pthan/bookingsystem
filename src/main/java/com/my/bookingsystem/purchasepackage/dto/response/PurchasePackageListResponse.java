package com.my.bookingsystem.purchasepackage.dto.response;

import com.my.bookingsystem.domain.shared.PackageItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PurchasePackageListResponse {
    private List<PurchasePackageResponse> items;
    private long totalRecords;
}
