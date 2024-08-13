package com.my.bookingsystem.model.response;

import com.my.bookingsystem.model.shared.TokenInfo;
import com.my.bookingsystem.model.shared.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private UserInfo userInfo;
   private TokenInfo tokenInfo;
}
