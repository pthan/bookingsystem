package com.my.bookingsystem.model.shared;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class TokenInfo {
     private String accessToken;
     private String refreshToken;
     private String accessTokenExpire;
     private String refreshTokenExpire;

}
