package com.my.bookingsystem.purchasepackage.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreditPackageRequest {
    @NotBlank
    private String packageName;

    @NotNull
    private Long countryId;

    @NotNull @Min(0)
    private Double price;

    @NotNull @Min(1)
    private Integer credit;

    // remaining cred

    @NotNull @Min(1)
    private Integer expireIn;
}
