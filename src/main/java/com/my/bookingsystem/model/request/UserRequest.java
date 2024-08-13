package com.my.bookingsystem.model.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserRequest    {
    private String name;
    private String phone;
    private String email;
    private String password;
}
