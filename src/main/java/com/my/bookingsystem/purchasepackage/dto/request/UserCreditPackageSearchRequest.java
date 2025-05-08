package com.my.bookingsystem.purchasepackage.dto.request;

import com.my.bookingsystem.domain.shared.SearchRequest;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class UserCreditPackageSearchRequest extends SearchRequest {
    private Boolean status;
    private Boolean isExpire;
    private Boolean hasBalance;
}
