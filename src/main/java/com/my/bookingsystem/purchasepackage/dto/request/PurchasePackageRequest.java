package com.my.bookingsystem.purchasepackage.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchasePackageRequest {
    private Long packageId;
    private Double amount;
    private String cardNumber;
    private String cvc;
    private String expiryDate;
    private String email;
}
