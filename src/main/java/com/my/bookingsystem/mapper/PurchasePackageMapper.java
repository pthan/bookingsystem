package com.my.bookingsystem.mapper;

import com.my.bookingsystem.purchasepackage.dto.response.PurchasePackageResponse;
import com.my.bookingsystem.purchasepackage.entity.PurchaseCreditPackage;
import org.mapstruct.Mapper;
@Mapper(componentModel = "spring")
public interface PurchasePackageMapper {

    PurchasePackageResponse toPurchasePackageResponse(PurchaseCreditPackage purchaseCreditPackage);

}
