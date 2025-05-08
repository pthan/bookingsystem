package com.my.bookingsystem.user.dto.response;

import com.my.bookingsystem.domain.shared.TokenInfo;
import com.my.bookingsystem.domain.shared.UserInfo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponse {
  private UserInfo userInfo;
   private TokenInfo tokenInfo;
}
