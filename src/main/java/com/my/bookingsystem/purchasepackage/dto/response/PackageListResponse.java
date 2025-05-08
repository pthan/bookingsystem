package com.my.bookingsystem.purchasepackage.dto.response;

import com.my.bookingsystem.domain.shared.PackageItem;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PackageListResponse {
    private List<PackageItem> items;
    private long totalRecords;
}
