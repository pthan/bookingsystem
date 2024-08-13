package com.my.bookingsystem.model.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PackageItem {
    private long id;
    String packageName;
    int credit;
    int expireIn;
    String guid;
    int price;
    String status;
    String countryName;
}
