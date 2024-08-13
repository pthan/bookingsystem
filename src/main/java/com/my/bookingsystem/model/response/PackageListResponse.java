package com.my.bookingsystem.model.response;

import com.my.bookingsystem.model.shared.PackageItem;
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
